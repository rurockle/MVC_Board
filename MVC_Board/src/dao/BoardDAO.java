package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static db.JdbcUtil.close;
import vo.BoardBean;

public class BoardDAO {
	/*======== [1] BoardDAO 인스턴스 생성 및 리턴을 위한 싱글톤 패턴===================*/
	private BoardDAO() {}
	
	private static BoardDAO instance;

	public static BoardDAO getInstance() {
	/* 기존 BoardDAO 인스턴스가 없을 때만 생성하여 return */
		if(instance == null) {
			instance = new BoardDAO();
		}
		return instance;
	}
	//==================================================================================
	/* [2] service 클래스로부터 JdbcUtil에서 제공받은 Connection 객체를 전달받기 */
	Connection con ;

	public void setConnection(Connection con) {
		this.con = con;
	}
	//==================================================================================
	
	/* [3]글쓰기 작업 수행을 위한 insertArticle()메서드 정의 */
	// 파라미터 BoardBean 객체(article), 리턴타입: int(insertCount)
	
	public int insertArticle(BoardBean article) {
		/* [3-1] 새 게시물 저장을 위해 현재 게시물 최대 글번호 조회 후, 
		 * 새 게시물 번호를 최대 글번호 +1을 하여 insert작업 수행 후, 결과를 리턴 */
		int insertCount = 0; // Insert 작업 결과를 저장할 변수
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			/* [3-2]새 게시물 번호 생성을 위해 기존 게시물 중 최대 글번호 조회 */
			String sql="SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			/* [3-3] 조회된 글 번호가 있을 경우, 해당 글 번호 +1 값을 새 글 번호로 지정 */
			int num = 1; //새 글 번호
			if(rs.next()) {
				num = rs.getInt(1) + 1;
			} else {
				num=1;
			}
			
			/* [3-4] board 테이블에 전달받은 게시물 정보를 INSERT 작업을 수행 */
			sql = "INSERT INTO board VALUES(?,?,?,?,?,?,?,?,?,?,now())";
			pstmt=con.prepareStatement(sql); //pstmt 에 connection 객체를 두번연결하여 나타나는 경고
			// 이미 알고 사용하므로 큰 문제되지 않음 // pstmt2로 변수를 달리 선언하면 없어짐
			pstmt.setInt(1, num); //글번호
			pstmt.setString(2, article.getBOARD_NAME());
			pstmt.setString(3, article.getBOARD_PASS());
			pstmt.setString(4, article.getBOARD_SUBJECT());
			pstmt.setString(5, article.getBOARD_CONTENT());
			pstmt.setString(6, article.getBOARD_FILE());
			pstmt.setInt(7, num); // board_re_ref(참조글 번호) - 원본글이므로 글번호로 지정
			pstmt.setInt(8, 0); // board_re_lev(들여쓰기 레벨) - 원본글이므로 들여쓰기 없음
			pstmt.setInt(9, 0); // board_re_seq(글 순서 번호) - 원본글이므로 0으로 지정
			pstmt.setInt(10, 0); // 조회수
//			pstmt.setString(11, article.getBOARD_IP());
			
			/* [3-4] SQL 실행 및 결과값을 int형으로 리턴받기 */
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
//			e.printStackTrace();
			System.out.println("BoardDAO - insertArticle() 에러!: "+e.getMessage());
		} finally {
			/* [3-5] PreparedStatement, ResultSet 객체 반환 */
			close(rs);
			close(pstmt);
		}
				
		return insertCount;
	}

	
	/* [4] 전체 게시물 수를 가져오기 위한 selectListCount() 메서드 정의 */
	public int selectListCount() {
		
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			/*[4-2] 게시물 수가 조회될 경우(게시물이 하나라도 존재할 경우)
			    => listCount에 조회된 게시물 수 저장*/
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("BoardDAO - selectListCount() 에러!");
		} finally {
			/* [4-3] PreparedStatement, ResultSet 객체 반환*/			
			close(rs);
			close(pstmt);
		}
		
		
		return listCount;
	}

	/* [5] 전체 게시물 조회를 위한 selectArticleList()메서드를 생성 */
	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		ArrayList<BoardBean> articleList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			/* [5-1]board 테이블의 모든 레코드 조회 */
			// 조건1) 정렬(ORDER BY절 사용): board_re_ref기준 내림차순, board_re_seq기준 오름차순
			// 조건2) 제한(limit): X번 레코드부터 limit개
			
			/* [5-2] 읽어올 시작 레코드 번호 계산 */
			// 현재 페이지 번호에서 1을 뺀 결과에 10을 곱하면 시작 레코드 번호
			int startRow = (page - 1) * 10;
			String sql = "SELECT * FROM board ORDER BY board_re_ref DESC, board_re_seq ASC"
					+ " LIMIT ?,?"; /* 띄어쓰기 주의! */
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow); // 시작 레코드 번호 전달
			pstmt.setInt(2, limit); // 게시물 수 전달
			rs = pstmt.executeQuery();
			
			/* [5-3] 조회된 레코드가 존재할 경우 BoardBean 객체에 레코드 정보를 저장 반복 */
			// ArrayList 객체에 BoardBean 객체 추가 반복
			articleList = new ArrayList<BoardBean>();
			while(rs.next()) {
			   BoardBean article = new BoardBean();
			   // 조회된 레코드 정보를 BoardBean객체에 저장
			   article.setBOARD_NUM(rs.getInt("board_num"));
			   article.setBOARD_NAME(rs.getString("board_name"));
			   article.setBOARD_SUBJECT(rs.getString("board_subject"));
			   article.setBOARD_CONTENT(rs.getString("board_content"));
			   article.setBOARD_FILE(rs.getString("board_file"));
			   article.setBOARD_RE_REF(rs.getInt("board_re_ref"));
			   article.setBOARD_RE_LEV(rs.getInt("board_re_lev"));
			   article.setBOARD_RE_SEQ(rs.getInt("board_re_seq"));
			   article.setBOARD_DATE(rs.getDate("board_date"));
			   article.setBOARD_READCOUNT(rs.getInt("board_readcount"));
			   
			/* [5-4] BoardBean 객체를 ArrayList 객체에 추가 */
			   articleList.add(article);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - selectArticleList() 에러!");
		} finally {
			/* [5-4] PreparedStatement, ResultSet 객체 반환*/			
			close(rs);
			close(pstmt);
		}
		
		
		
		return articleList;
	}

	/* [6] 게시물 1개에 대한 상세 정보를 조회하는 작업을 수행하는 selectArticle() 메서드 정의*/
	public BoardBean selectArticle(int board_num) {
//		System.out.println("select-article()메서드");

		BoardBean article = null;
		/* [6-1] board_num에 해당하는 게시물의 모든 정보를 조회하여 BoardBean객체에 저장 */
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				   article = new BoardBean();
				   article.setBOARD_NUM(rs.getInt("board_num"));
				   article.setBOARD_NAME(rs.getString("board_name"));
				   article.setBOARD_SUBJECT(rs.getString("board_subject"));
				   article.setBOARD_CONTENT(rs.getString("board_content"));
				   article.setBOARD_FILE(rs.getString("board_file"));
				   article.setBOARD_RE_REF(rs.getInt("board_re_ref"));
				   article.setBOARD_RE_LEV(rs.getInt("board_re_lev"));
				   article.setBOARD_RE_SEQ(rs.getInt("board_re_seq"));
				   article.setBOARD_DATE(rs.getDate("board_date"));
				   article.setBOARD_READCOUNT(rs.getInt("board_readcount"));

			}
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectArticle() 실패!!");
			e.printStackTrace();
		} finally {
			/* [6-2] PreparedStatement, ResultSet 객체 반환*/			
			close(rs);
			close(pstmt);
		}
		
		return article;
	}
	
   /* [7] 게시물 조회 후, 조회 수 증가를 위한 updateReadcount() 메서드 정의 */
	public int updateReadcount(int board_num) {
		
		int updateCount = 0;
		
		PreparedStatement pstmt = null;

		
		/* [7-1] board_num에 해당하는 게시물의 readcount 값을 1 증가시키기 */
		try {
			String sql="UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);		
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("BoardDAO - updateReadcount() 실패!!");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
//
//	public int insertIpInfo(String ip) {
//		int insertCount = 0;
//		
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			String sql = "INSERT INTO board_ip VALUES (?,now())";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, ip);
//			insertCount = pstmt.executeUpdate();
//		} catch (SQLException e) {
////			e.printStackTrace();
//			System.out.println("BoardDAO - insertIpInfo() 에러! : " + e.getMessage());
//		} finally {
//			// PreparedStatement, ResultSet 객체 반환
//			close(rs); // JdbcUtil.close(rs)
//			close(pstmt); // JdbcUtil.close(rs)
//		}
//		
//		return insertCount;
//	}
	/* [8] 전달받은 board_num에 해당하는 게시물의 패스워드와 전달받은 board_pass를 비교 */ 
	// 수정 가능 여부를 판별하는 isArticleBoardWriter() 메서드 정의
	public boolean isArticleBoardWriter(int board_num, String board_pass) {
		System.out.println(board_pass);
		boolean isArticleWriter = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT board_pass FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				/* [8-2] board_num에 해당하는 board_pass가 존재할 경우*/
				if(board_pass.equals(rs.getString("board_pass"))) {// 패스워드가 존재할 경우
					// 조회된 패스워드가 입력된 패스워드와 일치할 경우
					isArticleWriter = true;
				}
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - isArticleBoardWriter() 에러!!");
		}
		
		
		return isArticleWriter;
	}

	/* [9] 글수정(UPDATE)작업 수행을 위한 updateArticle()메서드 정의 */
	public int updateArticle(BoardBean article) {
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			/* [9-1] 글번호에 해당하는 게시물의 제목, 이름, 내용 수정(UPDATE) */
			//(sql 구문에서 명령어 띄어쓰기 외에 붙여쓰는 편이 좋다!!)
			String sql = "UPDATE board SET board_name=?,board_subject=?,board_content=? WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			/* [9-2] sql구문의 변수 세팅 */
			pstmt.setString(1,article.getBOARD_NAME());
			pstmt.setString(2, article.getBOARD_SUBJECT());
			pstmt.setString(3, article.getBOARD_CONTENT());
			pstmt.setInt(4, article.getBOARD_NUM());
			
			/* [9-3] UPDATE 결과 성공 여부를 판별하는 updateCount */
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - updateBoard() 에러!");
		}
		
		
		return updateCount;
	}


}
