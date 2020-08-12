package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardListService {

	/* [1-1] 전체 게시물 수 조회 요청을 위한 getListCount() 메서드 정의 */
	public int getListCount() {
		int listCount = 0;
		
	/* [1-2] JdbcUtil 클래스의 getConnection()메서드를 호출하여 Connection 객체 가져오기 */
	
		Connection con = getConnection();
		
	/* [1-3]BoardDAO 클래스의 getInstance() 메서드를 호출하여 BoardDAO객체 가져오기 */
	
		BoardDAO boardDAO = BoardDAO.getInstance();
		
	/* [1-4]BoardDAO 클래스의 setConnection() 메서드를 호출하여 BoardDAO객체 가져오기 */
		boardDAO.setConnection(con);
		
    /* [1-5]BoardDAO 클래스의 selectListCount()메서드를 호출하여, 전체 게시물 수 가져오기 */	
		// 파라미터 : 없음 | 리턴 타입: int(listCount)
		listCount = boardDAO.selectListCount();
//		System.out.println("전체 게시물 수: "+listCount);
		
	/* [1-6] close()메서드를 호출하여 Connection 객체 반환 */
		close(con);
		
		
		return listCount;
	}	

	/* [2-1] 전체 게시물 목록 조회 요청을 위한 getArticleList()메서드 정의 */
	public ArrayList<BoardBean> getArticleList(int page, int limit) {
//		System.out.println("BoardListService - getArticleList()");
		
		ArrayList<BoardBean> articleList = null;
		/* [2-2] JdbcUtil 클래스의 getConnection()메서드를 호출하여 Connection 객체 가져오기 */

		Connection con = getConnection();
		/* [2-3]BoardDAO 클래스의 getInstance() 메서드를 호출하여 BoardDAO객체 가져오기 */
		
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		/* [2-4]BoardDAO 클래스의 setConnection() 메서드를 호출하여 BoardDAO객체 가져오기 */
		boardDAO.setConnection(con);
		
		/* [2-5]BoardDAO 클래스의 selectArticleList()메서드를 호출하여 전체 게시물 목록 조회 */
		// 파라미터: page, limit | 리턴타입: ArrayList<BoardBean>(ariticleList)
		articleList = boardDAO.selectArticleList(page, limit);
		
		/* [2-6] close()메서드를 호출하여 Connection 객체 반환 */

		close(con);
		
		return articleList;
	}
}
