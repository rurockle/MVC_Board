package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.digester.SetPropertiesRule;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		//		System.out.println("보드모디파이!");
		/* [0] 게시물 수정을 위한 원본 게시물 요청 작업 수행
		 *     ==> BoardDetailService와 완전 동일한 작업 */
		
		/* [1] 파라미터로 전달받은 board_num, page 가져오기 */
		int board_num = Integer.parseInt((String)request.getParameter("board_num"));
		String page=request.getParameter("page");
		
		/* [2] 기존 상세 정보를 가져올 것이므로, BoardDetailService 클래스작업 */
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		request.setAttribute("article", article);
		request.setAttribute("page", page);
		
		forward = new ActionForward();
		forward.setPath("/board/qna_board_modify.jsp");
				
		
		return forward;
	}

}
