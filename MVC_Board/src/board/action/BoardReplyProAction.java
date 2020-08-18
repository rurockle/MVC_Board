package board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardReplyProService;
import board.vo.ActionForward;
import board.vo.BoardBean;

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
//		System.out.println("액션포워드프로!");
		
		/* [1] 파라미터로 전달받은 파라미터 가져오기 */
		int board_num = Integer.parseInt(request.getParameter("BOARD_NUM"));
		String page = request.getParameter("page");
		int board_re_ref = Integer.parseInt(request.getParameter("BOARD_RE_REF"));
		int board_re_lev = Integer.parseInt(request.getParameter("BOARD_RE_LEV"));
		int board_re_seq = Integer.parseInt(request.getParameter("BOARD_RE_SEQ"));
		
//		System.out.println(request.getParameter("page"));
//		System.out.println(request.getParameter("BOARD_NUM"));
//		System.out.println(request.getParameter("BOARD_RE_REF"));
//		System.out.println(request.getParameter("BOARD_RE_LEV"));
//		System.out.println(request.getParameter("BOARD_RE_SEQ"));
		
		/* [2] BoardBean(article)객체에 전달받은 파라미터 저장 */
		BoardBean article = new BoardBean();
				
		article.setBOARD_NUM(board_num);
		article.setBOARD_RE_REF(board_re_ref);
		article.setBOARD_RE_LEV(board_re_lev);
		article.setBOARD_RE_SEQ(board_re_seq);
		article.setBOARD_NAME(request.getParameter("BOARD_NAME"));
		article.setBOARD_PASS(request.getParameter("BOARD_PASS"));
		article.setBOARD_SUBJECT(request.getParameter("BOARD_SUBJECT"));
		article.setBOARD_CONTENT(request.getParameter("BOARD_CONTENT"));
		article.setBOARD_IP(request.getRemoteAddr());
		
		/* [3] BoardReplyProService 클래스의 registReplyArticle() 메서드를 호출 */
		// 답변글 등록 작업 요청 후 처리 결과 리턴받기
		// => 파라미터 : BoardBean, 리턴타입 : boolean(isReplySuccess)
		BoardReplyProService service = new BoardReplyProService();
	
		boolean isReplySuccess = service.resistReplyArticle(article); 
		
				
		/* [4] 답변글 등록이 실패했을 경우 */
		// 자바스크립트를 사용하여 "답글 등록 실패!" 메세지 출력 후 이전 페이지로 이동
		if(!isReplySuccess) {
		    response.setContentType("text/html;charset=UTF-8"); // js출력을 위한 문서 타입 설정
			PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
			// println()메서드를 사용하여 자바스크립트를 문자열로 출력
			out.println("<script>"); // JS시작
			out.println("alert('답글 등록 실패!')"); //오류메시지 출력
			out.println("history.back()"); // 이전페이지 이동
			out.println("</script>"); // JS종료
		}else {
		
		/* [5] 답변글 등록이 성공했을 경우 */
		// BoardList.bo 주소로 포워딩(파라미터로 페이지번호(page)를 전달)
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("BoardList.bo?page="+page);
		}
		return forward;
	}

}
