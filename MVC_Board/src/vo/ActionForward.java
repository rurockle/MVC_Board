package vo;

public class ActionForward {
	
	// 서블릿에서 클라이언트로부터 요청을 전달받아 처리한 후
	// 지정한 View 페이지로 포워딩할 때
	// 포워딩할 View 페이지의 주소(URL)와 포워딩 방식(Redirect or Dispatch)을
	// 공통으로 다루기 위한 클래스
	
	private String path; // 포워딩 주소 지정할 변수
	private boolean isRedirect; // 포워딩 방식 지정할 변수
	// ==> true: Redirect 방식 | false: Dispatch 방식
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
		
}
