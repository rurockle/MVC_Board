package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardDetailService;
import board.vo.ActionForward;
import board.vo.BoardBean;

/*[1] 글 상세 내용 보기 요청을 처리하는 Action 클래스*/
public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//			System.out.println("보드디테일 확인!!!");
			ActionForward forward = null;
		/* [2] 게시물 1개 의 정보를 조회하기 위한 준비 작업 수행 */
		// 파라미터로 전달받은 글번호(BOARD_NUM)과 현재페이지(page)를 읽어와서
		// 글번호를 BoardDetailService 클래스에 전달
			
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			String page=request.getParameter("page");
			
		/* [3] BoardDetailService 클래스의 인스턴스 생성 후 */
		// getArticle()메서드를 호출하여 게시물 상세내용 조회 작업 요청
		// ==> 파라미터: 글번호 board_num, 리턴타입 BoardBean(article)
			BoardDetailService boardDetailService = new BoardDetailService();
			BoardBean article = boardDetailService.getArticle(board_num);
		
		/* [4] request 객체에 BoardBean 객체와 page를 저장 */
		
			request.setAttribute("article", article);
			request.setAttribute("page", page);
			
		/* [5] ActionForward 객체 생성 및 qna_board_view.jsp로 포워딩(dispatch방식) */
			forward = new ActionForward();
			forward.setPath("/board/qna_board_view.jsp");
		return forward;
	}
	
}
