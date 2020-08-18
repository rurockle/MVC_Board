package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardDetailService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
//		System.out.println("보드리플폼액션");
		/* [1] 답변글 작성을 위한 폼 생성을 위해 기존 게시물의 상세 내용을 가져오기*/
		// 뷰페이지(qna_board_reply.jsp)로 포워딩
		
		/* [2] 식별하기 위해 게시판 번호, 페이지 가져오기 */
		int board_num = Integer.parseInt((String)request.getParameter("board_num"));
		String page=request.getParameter("page");
		
		/* [3] 상세내용 가져오기 */
		BoardDetailService service = new BoardDetailService();
		BoardBean article = service.getArticle(board_num);
		
		/* [4] request객체에 BoardBean 객체와 page를 저장 */
		request.setAttribute("article", article);
		request.setAttribute("page", page);
		
		/* [5] ActionForward 객체 생성 및 board 디렉토리 내의 qna_board_reply.jsp로 포워딩 */
		// Dispatch방식
		forward = new ActionForward();
		forward.setPath("/board/qna_board_reply.jsp");
		
		return forward;
	}

}
