package svc;

import static db.JdbcUtil.*;


import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardReplyProService {
	
	/* [0] 답변글 등록 요청 작업을 위한 registReplyArticle() 메서드 호출 */
	public boolean resistReplyArticle(BoardBean article) {
		
//		System.out.println("보드리플프로서비스");
		/* [1] 등록 성공 여부 처리 결과를 저장할 boolean 타입 변수 선언 */
		boolean isReplySuccess = false;
		
		/* [2] (공통) connection객체 가져오기 */		
		Connection con = getConnection();
		
		/* [3] (공통) BoardDAO 객체 가져오기 */
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		/* [4] (공통) BoardDAO 객체의 setConnection() 메서드를 호출하여 Connection 객체를 전달*/
		boardDAO.setConnection(con);
		
		/* [5] BoardDAO 클래스의 insertReplyArticle() 메서드를 호출하여 답글 등록 작업 수행 */
		// 파라미터: BoardBean, 리턴타입: int(insertCount)
		
		int insertCount = boardDAO.insertReplyArticle(article);		
		
		/* [6] 작업 수행 결과 판별하여 commit rollback결정 */
		
		if(insertCount>0) {
			commit(con);
			isReplySuccess = true;
		}else {
			rollback(con);
		}
		
		/* [6] (공통) 자원반환 */
		close(con);
		
		
		return isReplySuccess;
	}

}
