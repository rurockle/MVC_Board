package svc;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardModifyProService {

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
		
		
		
		close(con);
		
		return isArticleWriter;
	}

	public boolean modifyArticle(BoardBean article) {
		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		int modifyCount = boardDAO.updateBoard(article);
		
		if(modifyCount >0) { // 작업이 성공했을 경우
			
			commit(con);
			
			isModifySuccess = true;
		}else {
			
		
		}
		
	  
		    close(con);
		
		
		return isModifySuccess;
	}

	
}
