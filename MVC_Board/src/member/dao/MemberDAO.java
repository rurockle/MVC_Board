package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static member.db.JdbcUtil.*;

import member.vo.MemberBean;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() {}

	public static MemberDAO getInstance() {
		return instance;
	};
	
	// ----------------------------------------------
	
	Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}

	// 회원 중복 여부 체크를 위한 dupCheckMember() 메서드 정의
	public int dupCheckMember(MemberBean member) {
		int checkResult = 1;
		// => 0 : 아이디 중복, -1 : 이메일 중복, 1 : 중복 없음(가입 가능)
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 아이디 또는 이메일이 중복되는 레코드의 아이디와 이메일을 조회
			// => 조회 결과와 MemberBean 객체의 아이디, 이메일을 검사하여
			//    아이디가 중복일 경우 checkResult 를 0, 이메일이 중복일 경우 -1 로 변경
			//    만약, 중복되는 데이터가 없을 경우 아무 작업도 수행하지 않음
			String sql = "SELECT id,email FROM member WHERE id=? OR email=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getEmail());
			rs = pstmt.executeQuery();
			
			// 조회 결과에 대한 중복 여부 판별
			if(rs.next()) {
				// 아이디 중복 체크 결과가 맞으면 0, 
				// 아니면 이메일 중복 체크 결과가 맞으면 -1
				if(member.getId().equals(rs.getString("id"))) {
					checkResult = 0;
				} else if(member.getEmail().equals(rs.getString("email"))) {
					checkResult = -1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MemberDAO - dupCheckMember() 오류!");
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return checkResult;
	}

	// 회원 가입 처리를 위한 insertMember() 메서드 정의
	public int insertMember(MemberBean member) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO member VALUES (null,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getId());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getEmail());
			
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MemberDAO - insertMember() 오류!");
		} finally {
			close(pstmt);
		} 
		
		return insertCount;
	}

	/* [3] id, passwd를 판별하여 로그인 가능 여부를 리턴하는 loginMember() 메서드 정의 */
	public int loginMember(MemberBean member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int checkResult = 0;

		// 아이디 또는 이메일이 중복되는 레코드의 아이디와 이메일을 조회
		// => 조회 결과와 MemberBean 객체의 아이디, 이메일을 검사하여
		//    아이디가 없을 경우 checkResult 를 0, 비밀번호 틀릴 경우 -1 로 변경
		//    만약, 둘다 맞을 경우 1
		try {
			/* [3-1] id를 사용하여 passwd 검색 */
			String sql = "SELECT passwd FROM member WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			rs = pstmt.executeQuery();

			
			/* [3-2-1] 아이디가 존재할 경우 */
			if(rs.next()) {
			// 아이디 중복 체크 결과가 맞으면 0, 
			// 아니면 이메일 중복 체크 결과가 맞으면 -1
			/* [3-3-1] 패스워드가 일치할 경우 */
				if(rs.getString("passwd").equals(member.getPasswd())) {
					checkResult = 1;
				} else { /* [3-3-2] 패스워드가 일치하지 않을 경우 */
					checkResult=-1;
				}
			} /* [3-2-2]아이디가 존재하지 않을 경우(초기값 0으로 줘서, 비교 생략이 가능 */ 
			
		} catch (SQLException e) {
			System.out.println("MemberDAO - selectMember() 오류!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return checkResult;
	}

	public ArrayList<MemberBean> selectMemberList() {
		
		PreparedStatement pstmt = null;
		
		
		ArrayList<MemberBean> memberList = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			memberList = new ArrayList<MemberBean>();
			while(rs.next()) {
				MemberBean member = new MemberBean();
				member.setId(rs.getString("id"));
				member.setIdx(rs.getInt("idx"));
				member.setPasswd(rs.getString("passwd"));
				member.setEmail(rs.getString("email"));
				member.setRegDate(rs.getDate("regDate"));
				member.setName(rs.getString("name"));
				
				memberList.add(member);
			}
			
		} catch (SQLException e) {
			System.out.println("MemberDAO - selectMemberList() 오류!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}	
		return memberList;
	}

	
	// 회원목록 조회작업을 위한 selectMemberList 메서드 정의 - 파라미터 있을 경우
	public ArrayList<MemberBean> selectMemberList(String orderTarget, String orderType) {
		
		PreparedStatement pstmt = null;
		
		
		ArrayList<MemberBean> memberList = null;
		ResultSet rs = null;
		try {
			// ORDER BY 절의 항목을 문자열 결합으로 생성
			String sql = "SELECT * FROM member ORDER BY " + orderTarget + " " + orderType;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			memberList = new ArrayList<MemberBean>();
			while(rs.next()) {
				MemberBean member = new MemberBean();
				member.setId(rs.getString("id"));
				member.setIdx(rs.getInt("idx"));
				member.setPasswd(rs.getString("passwd"));
				member.setEmail(rs.getString("email"));
				member.setRegDate(rs.getDate("regDate"));
				member.setName(rs.getString("name"));
				
				memberList.add(member);
			}
			
		} catch (SQLException e) {
			System.out.println("MemberDAO - selectMemberList()-파라미터있음 오류!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		

		return memberList;
	}
	
}














