package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.vo.ActionForward;

public class MemberLogoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		ActionForward forward = null;
//		System.out.println("멤버로그아웃액션");\
		/* [2] HttpSession 객체 가져와서 invalidate() 메서드를 호출하여 세션 제거 */
		HttpSession session = request.getSession();
//		session.removeAttribute("id"); // 하나의 세션값만 지우려면 removeAttribute() 사용
		session.invalidate(); 
		
		/* [3] index.jsp 페이지로 포워딩 */
//		forward = new ActionForward();
//		forward.setRedirect(true);
//		forward.setPath("./"); /* .(현재위치에서)/최상위 : 현재 프로젝트의 최상위 위치로 이동*/	
		
		/* 또는[3-1] 자바스크립트 사용하여 메시지 출력 후 이동 */
		response.setContentType("text/html;charset=UTF-8"); // 문서 타입 설정
		PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
		out.println("<script>"); // 자바스크립트 시작
		out.println("alert('로그아웃 되었습니다!')"); // 오류 메세지 출력
		out.println("location.href='./'"); // 이전 페이지로 이동
		out.println("</script>"); // 자바스크립트 끝
		
		return forward;
	}

}
