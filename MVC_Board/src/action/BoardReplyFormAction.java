package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		System.out.println("보드리플폼액션");
		
		int board_num = Integer.parseInt((String)request.getParameter("board_num"));
		String page=request.getParameter("page");
		
		BoardDetailService service = new BoardDetailService();
		BoardBean article = service.getArticle(board_num);
		
		request.setAttribute("article", article);
		request.setAttribute("page", page);
		
		forward = new ActionForward();
		forward.setPath("/board/qna_board_reply.jsp");
		
		return forward;
	}

}
