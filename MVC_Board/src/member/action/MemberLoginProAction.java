package member.action;

import java.io.PrintWriter;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.svc.MemberLoginProService;
import member.vo.ActionForward;
import member.vo.MemberBean;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		/* [1] 서블릿 주소로 넘어오는지 확인 */
//		System.out.println("멤버로그인프로액션");
		
//		String id = request.getParameter("id");
//		String passwd = request.getParameter("passwd");
//		
//		System.out.println(id+", "+passwd);
		/* [2] member_login_jsp 페이지로부터 전달받은 파라미터(아이디 패스워드)를 MemberBean객체에 저장 */ 
		
		MemberBean member = new MemberBean();
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
		
		/* [3] MemberLoginProService 클래스의 loginMember() 메서드를 호출하여 로그인 요청 작업 수행 */
		// 파라미터: MemberBean | 리턴타입: int(loginResult)
		MemberLoginProService service = new MemberLoginProService();
		
		int loginResult = service.loginMember(member);
		
		/* [4] 로그인 작업 요청 결과 판별 */
		// 0: 아이디없음| -1 : 패스워드 틀림 메시지를 자바스크립트로 출력 및 이전페이지로 이동
		// 1일 때 index.jsp 페이지로 포워딩
		if(loginResult == 0 || loginResult == -1) {
			String resultStr = "";
			
			if(loginResult == 0) {
				resultStr = "아이디 없음";
			} else if(loginResult == -1) {
				resultStr = "비밀번호 틀림";
			}
			
			response.setContentType("text/html;charset=UTF-8"); // 문서 타입 설정
			PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
			out.println("<script>"); // 자바스크립트 시작
			out.println("alert('" + resultStr + "')"); // 오류 메세지 출력
			out.println("history.back()"); // 이전 페이지로 이동
			out.println("</script>"); // 자바스크립트 끝
		}else {

			forward = new ActionForward();
			forward.setRedirect(true);
			/* [5] 로그인 작업 수행 후, 로그인 성공 시 세션 처리 */
			/* [5-1] HttpSession 인터페이스를 사용하여 request 객체로부터 세션 객체를 전달받아 저장 */
			HttpSession session = request.getSession();
			/* [5-2] HttpSession 객체의 setAttribute() 메서드를 호출하여 세션 정보 저장*/
			session.setAttribute("id", member.getId());
			forward.setPath("./"); /* .(현재위치에서)/최상위 : 현재 프로젝트의 최상위 위치로 이동*/	
		}
		
		
		return forward;
	}

}
