package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;
import board.vo.BoardBean;

public class BoardDetailService {
	public BoardBean getArticle(int board_num) {
		/* [1] 글 상세내용 조회 작업 요청 처리 결과를 저장할 BoardBean 타입 변수 선언 */
		BoardBean article = null;
//		System.out.println("보드디테일서비스!!");
		
		/* [2] 공통 Connection 객체 가져오기 */
		Connection con = getConnection();		
		
		/* [3] 공통 BoardDAO 클래스로부터 객체 가져오기 */
		BoardDAO boardDAO = BoardDAO.getInstance();
	
		/* [4] 공통 BoardDAO 객체의 setConnection() 메서드를 호출하여 Connection 객체를 전달*/
		boardDAO.setConnection(con);
	   
		/* [5] BoardDAO 객체의 XXX메서드를 호출하여 XXX작업 수행 및 결과 리턴 받기
		 *     selectArticle()메서드를 호출하여 글 상세내용 조회 작업 수행 및 결과 리턴받아
		 * ==> 파라미터: 글번호(board_num) 리턴값: BoardBean(article)*/ 
		article = boardDAO.selectArticle(board_num);
		
		/* [8] 조회된 게시물이 존재할 경우(article != null) 조회수 업데이트를 수행 */
		if(article != null) {
			// 조회수 증가를 위해 BoardDAO 객체의 updateReadcount() 메서드 호출
			// 파라미터: 글번호(board_num), 리턴타입: int(updateCount)
			
			int updateCount = boardDAO.updateReadcount(board_num);
			
			/* [8-1] 조회수 증가에 성공했을 경우 commit, 실패했을 경우 rollback */
			if(updateCount > 0) {
				commit(con);
			}else {
				rollback(con);
			}
		}
		
		
	    /* [6] JdbcUtil 객체로부터 가져온 Connection 객체를 반환(공통) */
		close(con);
		
		/* [7] 작업 처리결과 리턴 */
		return article;
	}
}
