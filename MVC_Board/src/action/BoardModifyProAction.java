package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import svc.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("보드모디파이프로!!");
		/* [1] 포워딩 방식을 지정할 변수 설정 */
		ActionForward forward = null;
		
		/* [2-1] 수정 성공 여부 저장할 변수 */
		boolean isModifySuccess = false;
		
		/* [2-2] 파라미터로 전달받은 board_num, page 가져오기 */
		int board_num = Integer.parseInt((String) request.getParameter("board_num"));
		String page=request.getParameter("page");
		
		/* [2] 글수정 작업 요청을 위한 BoardModifyProService 클래스의 modifyArticle()메서드 호출 */
		// ==> 단, 먼저 본인 여부를 확인하여 본인일 경우에만 수정 가능하도록 작업 수행
		/* [2-3] BoardModifyProService 인스턴스 생성 */
		BoardModifyProService service = new BoardModifyProService();
		
		/* [2-4] isArticleWriter()메서드를 호출하여 패스워드 일치여부 판별 */
		// 파라미터: 글번호(board_num), 비밀번호 | 리턴타입: boolean(isRightUser)
		String board_pass = request.getParameter("BOARD_PASS");
		boolean isRightUser = service.isArticleWrite(board_num, board_pass);
		
		/* [2-5] 일치여부 확인 후처리 작업 */
		if(!isRightUser) { /*글 수정 실패 시*/
			// 자바스크립트를 사용하여 오류 메시지 출력하고, 이전 페이지로 이동
			// PrintWriter 객체를 사용하여 자바스크립트 코드를 출력
			// response 객체를 사용하여 문서타입 설정 및 PrintWriter 객체 가져오기
	        response.setContentType("text/html;charset=UTF-8"); // js출력을 위한 문서 타입 설정
			PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
			// println()메서드를 사용하여 자바스크립트를 문자열로 출력
			out.println("<script>"); // JS시작
			out.println("alert('글 수정 권한이 없습니다!')"); //오류메시지 출력
			out.println("history.back()"); // 이전페이지 이동
			out.println("</script>"); // JS종료
		} else { /* [2-6] 요청 성공했을 경우 */ 
			// 수정폼에서 입력된 데이터를 BoardBean 객체(article)에 저장 후, 
			// BoardModifyProService 클래스의 modifyArticle()메서드를 호출하여 
			// 게시물 수정(UPDATE) 작업 요청
			// 파라미터: BoardBean(article) | 리턴타입: boolean(isModifySucess)
			BoardBean article = new BoardBean();
			
			article.setBOARD_NAME(request.getParameter("BOARD_NAME"));
			article.setBOARD_SUBJECT(request.getParameter("BOARD_SUBJECT"));
			article.setBOARD_CONTENT(request.getParameter("BOARD_CONTENT"));
			article.setBOARD_NUM(board_num);
			
			isModifySuccess = service.modifyArticle(article);
			/* [2-7] 수정작업이 실패했을 경우*/ 
			// 자바스크립트를 사용하여 "글수정실패" 메시지 출력 후 이전페이지로 이동
			
			if(!isModifySuccess) {
				response.setContentType("text/html;charset=UTF-8"); // js출력을 위한 문서 타입 설정
				PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
				// println()메서드를 사용하여 자바스크립트를 문자열로 출력
				out.println("<script>"); // JS시작
				out.println("alert('글 수정 실패!')"); //오류메시지 출력
				out.println("history.back()"); // 이전페이지 이동
				out.println("</script>"); // JS종료
			}else {
			/* [2-8] 수정작업이 성공했을 경우 */
			// 게시물 상세내용요청을 위해 BoardDetail.bo 주소로 포워딩(redirect) 
			// 파라미터로 게시물 번호와 페이지 전달(주소가 바뀌니까! )
			forward = new ActionForward();
			forward.setRedirect(true);
			
			request.setAttribute("board_num", board_num);
			request.setAttribute("page", page);
			
			forward.setPath("BoardDetail.bo?board_num="+board_num+"&page="+page);
			}
		}
			

		return forward;
	}

}
