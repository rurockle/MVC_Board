package board.svc;

import static board.db.JdbcUtil.*;

import java.sql.Connection;

import board.dao.BoardDAO;
import board.vo.BoardBean;



public class BoardWriteProService {
	/*[0] 글쓰기 작업 요청을 위한 registArticle() 메서드 정의 */
	public boolean registArticle(BoardBean boardBean) {

//		System.out.println("BoardWriteProService - registArticle();");
		
       /* [1] 글쓰기 작업 요청 처리 결과를 저장할 boolean 타입 변수 선언 */
		boolean isWriteSuccess = false;
		
	   /* [2]JdbcUtil 객체로부터 Connection Pool에 저장된 Connection 객체 가져오기(공통) */
		
		Connection con = getConnection(); //static 메서드를 그대로 import
		
		
	   /* [3] BoardDAO 클래스로부터 BoardDAO 객체 가져오기(공통) */
		BoardDAO boardDAO = BoardDAO.getInstance();
		
	   /* [4] BoardDAO 객체의 setConnection() 메서드를 호출하여 Connection 객체를 전달(공통) */
		boardDAO.setConnection(con);
		
	   /* [5] BoardDAO 객체의 XXX메서드를 호출하여 XXX작업 수행 및 결과 리턴 받기
	    *     InsertArticle()메서드를 호출하여 글등록 작업 수행 및 결과 리턴 받아 처리
	    * ==> 파라미터: BoardBean객체, 리턴값: int(insertCount)*/ 
		
		int insertCount = boardDAO.insertArticle(boardBean);
		
		/*[5-1] 리턴 값에 대한 결과 처리 */
		if(insertCount >0) { // 작업이 성공했을 경우
			
			//Insert 작업이 성공했을 경우, 트랜잭션 적용을 위해
			//JdbcUtil 클래스의 commit() 메서드를 호출하여 commit 작업 수행
			commit(con);
			
			//작업처리 결과를 성공으로 표시하기 위해 isWriteSuccess를 true로 지정
			isWriteSuccess = true;
		}else { // 작업이 실패했을 경우
			//Insert 작업이 실패했을 경우, 트랜잭션 취소를 위해
			//JdbcUtil 클래스의 rollback() 메서드를 호출하여 rollback 작업 수행
			/* (기본값 false이므로) isWriteSucess=false 작업 필요 없음 */ 
		}
		
	   /* [6] JdbcUtil 객체로부터 가져온 Connection 객체를 반환(공통) */
		    close(con);
		
	   /* [7] 처리 결과 리턴 */
		return isWriteSuccess;
	}

//	public void registIp(String ip) {
//		Connection con = getConnection(); // JdbcUtil.getConnection()
//		
//		BoardDAO boardDAO = BoardDAO.getInstance();
//		
//		boardDAO.setConnection(con);
//		
//		int insertCount = boardDAO.insertIpInfo(ip);
//	
//  }
}
