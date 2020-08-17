package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import vo.ActionForward;

/*[1] 글 삭제 요청을 처리하는 Action 클래스*/
public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
//		System.out.println("보드삭제프로");
		
		/* [2] 글번호, 페이지, 글비밀번호를 받아옴 */
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String nowPage = request.getParameter("page");
		String board_pass = request.getParameter("BOARD_PASS");
//		System.out.println(board_num+page+pass);
		
		/* [3] 입력받은 패스워드가 게시물의 패스워드와 일치하는지(본인 여부) 확인 작업 요청 */
		// 이미 Modify에서 작업을 처리했으므로, ModifyProService를 호출하여 일치여부 작업 처리하거나
		// DeleteProService에 그대로 가져와서 메서드 만드는 2가지 방법이 있음 (여기서는 2번)
		// 파라미터: 글번호(board_num), 패스워드(board_pass) | 리턴타입: boolean(isArticleWriter)
				
		BoardDeleteProService deleteService = new BoardDeleteProService();
		boolean isArticleWriter = deleteService.isArticleWrite(board_num, board_pass);
		
		/* [4] 리턴받은 boolean 타입 결과값을 사용하여 본인 여부 판별 결과 처리 */
		//=> isArticleWriter가 false이면 자바스크립트 사용하여 "삭제권한없습니다" 출력 후 이전페이지
		//=> 아니면, "패스워드 일치!" 출력
		
		if(!isArticleWriter) {/*본인확인 실패 시*/
			/* [4-1]자바스크립트를 사용하여 오류 메시지 출력하고, 이전 페이지로 이동 */
			// PrintWriter 객체를 사용하여 자바스크립트 코드를 출력
			// response 객체를 사용하여 문서타입 설정 및 PrintWriter 객체 가져오기
	        response.setContentType("text/html;charset=UTF-8"); // js출력을 위한 문서 타입 설정
			PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
			// println()메서드를 사용하여 자바스크립트를 문자열로 출력
			out.println("<script>"); // JS시작
			out.println("alert('글 삭제 권한이 없습니다!')"); //오류메시지 출력
			out.println("history.back()"); // 이전페이지 이동
			out.println("</script>"); // JS종료
		} else {/* 본인확인 성공 시 */
//			System.out.println("패스워드 일치");
			/* [4-2] BoardDeleteProSertvice의 deleteArticle() 메서드 호출하여 삭제 작업 */
			// 파라미터: 글번호(board_num) | 리턴타입: boolean(isDeleteSuccess)
			boolean isDeleteSuccess = deleteService.deleteArticle(board_num);
			
			/* [5] 삭제 성공여부에 따른 개별 작업 */
			if(!isDeleteSuccess) {
				/* [5-1] 자바스크립트를 사용하여 오류 메시지 출력하고, 이전 페이지로 이동 */ 
				response.setContentType("text/html;charset=UTF-8"); // js출력을 위한 문서 타입 설정
				PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
				// println()메서드를 사용하여 자바스크립트를 문자열로 출력
				out.println("<script>"); // JS시작
				out.println("alert('글 삭제 실패!')"); //오류메시지 출력
				out.println("history.back()"); // 이전페이지 이동
				out.println("</script>"); // JS종료	
			}else {
				/* [5-2] 삭제 성공 시, isDeleteSuccess=true / 삭제한 페이지 List 뜨게 포워딩 */
			isDeleteSuccess=true;
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("BoardList.bo?page="+nowPage);
			
			} 
		}
		
		return forward;
	}

}
