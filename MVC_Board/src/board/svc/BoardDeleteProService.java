package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;

public class BoardDeleteProService {
	
	/* [0] 패스워드 일치 여부 조회 요청을 위한 isArticleWriter()메서드 정의 */
	public boolean isArticleWrite(int board_num, String board_pass) {
		
		/* [1] 패스워드 일치 여부 처리 결과를 저장할 boolean 타입 변수 선언 */
		boolean isArticleWriter = false;
		
		/* [2] (공통) connection객체 가져오기 */		
		Connection con = getConnection();
		
		/* [3] (공통) BoardDAO 객체 가져오기 */
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		/* [4] (공통) BoardDAO 객체의 setConnection() 메서드를 호출하여 Connection 객체를 전달*/
		boardDAO.setConnection(con);
		
		/* [5] BoardDAO 클래스의 isArticleBoardWriter()메서드 호출 작업 */ 
		// 전달받은 패스워드와 board_num에 해당하는 게시물의 패스워드를 판별
		// 파라미터: 글번호(board_num), 패스워드(board_pass) | 리턴타입: boolean(isArticleWriter)
		isArticleWriter = boardDAO.isArticleBoardWriter(board_num, board_pass);
		
		
		/* [6] (공통) 자원반환 */
		close(con);
		
		return isArticleWriter;
	}

	/* [7]글 삭제 작업 요청을 위한 deleteArticle()메서드 정의 */
	public boolean deleteArticle(int board_num) {
		/* [1] 패스워드 일치 여부 처리 결과를 저장할 boolean 타입 변수 선언 */
		boolean isDeleteSucess = false;
		
		/* [2] (공통) connection객체 가져오기 */		
		Connection con = getConnection();
		
		/* [3] (공통) BoardDAO 객체 가져오기 */
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		/* [4] (공통) BoardDAO 객체의 setConnection() 메서드를 호출하여 Connection 객체를 전달*/
		boardDAO.setConnection(con);
		
		/* [5] BoardDAO 클래스의 deleteArticle() 메서드 호출 작업 */
		// DAO 작업 성공 여부 확인을 위한 deleteCount 
		int deleteCount = boardDAO.deleteArticle(board_num);
		
		/* [6] 글삭제 성공 시, commit & isDeleteSuccess=true | 실패시 rollback */
		if(deleteCount>0) {
			commit(con);
			isDeleteSucess = true;
		}else {
			rollback(con);
		}
		
		close(con);
		return isDeleteSucess;
	}
	
	
}
