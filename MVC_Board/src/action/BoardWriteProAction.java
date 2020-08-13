package action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BoardWriteProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 게시판 글 작성 후 DB작업을 위한 준비 작업
		// ==> 준비완료 후, BoardWriteProService 클래스 인스턴스 생성하여 작업 수행
//		System.out.println("ActionForward!!");
		ActionForward forward = null;
		
		// request 객체로부터 현재 서블릿 컨텍스트 객체를 가져오기
		ServletContext context = request.getServletContext();
		
		
		// 파일이 업로드 될 폴더 설정
		String saveFolder = "/boardUpload"; // 업로드 폴더(가상 폴더) 이름 지정
		// 지정된 이름의 실제 폴더 위치 가져오기
		String realFolder = context.getRealPath(saveFolder); 
		// 이클립스 실제 업로드 폴더는
		// 워크스페이스/.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps 폴더의
		// 자신의 프로젝트명 내에 실제 폴더 구조가 만들어짐
//		System.out.println("가상폴더: "+saveFolder);
//		System.out.println("실제폴더: "+realFolder);
		
		int maxSize = 10*1024*1024; // 최대 업로드 가능 크기 지정 1024(1kbyte) 
		// byte 단위를 기준으로 원하는 사이즈를 단위별로 계산(1024byte=1kByte *1024=1MByte * 10 = 10MB)
		
												/* 객체정보,파일저장위치,최대크기,폰트속성,같은이름파일처리 */
		MultipartRequest multi = new MultipartRequest(
				request, // request 객체
				realFolder, // 계산된 실제 업로드 폴더 위치 전달
				maxSize, // 최대 업로드 가능 파일 크기
				"utf-8", // 캐릭터 셋
				new DefaultFileRenamePolicy()); // 동일한 파일 존재 시 이름 변경 가능 객체(바로 숫자를 추가)

	
		String name=multi.getParameter("BOARD_NAME");
		String pass=multi.getParameter("BOARD_PASS");
		String subject=multi.getParameter("BOARD_SUBJECT");
		String content=multi.getParameter("BOARD_CONTENT");
		String file=multi.getFilesystemName("BOARD_FILE");
		
//		System.out.println("이름: "+name+", 비번: "+pass+", 제목: "+subject+", 내용: "+content+", 파일명: "+file);
		
		// 게시물 1개 정보를 저장할 BoardBean() 객체 생성
		BoardBean boardBean = new BoardBean();
		boardBean.setBOARD_NAME(multi.getParameter("BOARD_NAME"));
		boardBean.setBOARD_PASS(multi.getParameter("BOARD_PASS"));
		boardBean.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT"));
		boardBean.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT"));
		// 업로드 될 파일의 경우 getOriginamFileName() 또는 getFilesystemName() 으로 가져오기
		// 1) 업로드하는 파일의 원본 이름을 사용할 경우
			boardBean.setBOARD_FILE(
						multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		// 2) 업로드하는 파일이 중복될 때, 이름이 변경된 실제 업로드 된 파일명을 사용할 경우
//			boardBean.setBoard_file(
//						multi.getFilesystemName((String)multi.getFileNames().nextElement()));
		/* BoardWriteProService 클래스의 인스턴스를 생성하여*/
		// registArticle() 메서드를 호출하고, 글쓰기를 위한 BoardBean 객체 전달
		// ==> 글쓰기 작업 요청 처리 후, 결과를 boolean 타입으로 리턴받아 포워딩 처리
			// --------------------------------------------------
			/* 글 작성 IP 주소 가져와서 저장하기 */
			boardBean.setBOARD_IP(request.getRemoteAddr());				
			// --------------------------------------------------
		BoardWriteProService boardWriteProService = new BoardWriteProService();
			
		boolean isWriteSuccess = boardWriteProService.registArticle(boardBean); //throws Exception시 여기로 넘어옴
		
//		System.out.println("isWriteProService = "+ isWriteSuccess );
		
		/* 글쓰기 작업 요청 처리 결과(isWriteSuccess)를 통해 오류 메시지 출력 및 포워딩 */
		if (!isWriteSuccess) { /* 요청 실패했을 경우 */
			// 자바스크립트를 사용하여 오류 메시지 출력하고, 이전 페이지로 이동
			// PrintWriter 객체를 사용하여 자바스크립트 코드를 출력
			// response 객체를 사용하여 문서타입 설정 및 PrintWriter 객체 가져오기
			response.setContentType("text/html;charset=UTF-8"); // js출력을 위한 문서 타입 설정
			PrintWriter out = response.getWriter(); // PrintWriter 객체 가져오기
			// println()메서드를 사용하여 자바스크립트를 문자열로 출력
			out.println("<script>"); // JS시작
			out.println("alert('글 등록 실패!')"); //오류메시지 출력
			out.println("history.back()"); // 이전페이지 이동
			out.println("</script>"); // JS종료
		} else { /* 요청 성공했을 경우 */ 
			// ActionForward 객체를 생성하여 포워딩 방식 및 URL 지정
			forward = new ActionForward();
			// BoardList.bo 서블릿 주소를 새로 요청하고, request 객체 유지할 필요가 없음
			// ==> Redirect 방식으로 포워딩 처리
			forward.setRedirect(true); //Redirect 방식 지정
			forward.setPath("BoardList.bo");
		}
		
//==============================================================================================		
//		System.out.println(multi.getParameter("BOARD_FILE")); 
		// 파일명은 getParameter로 하면 null값이 뜨므로 
//		// getOriginamFileName(), getFilesystemName()으로 해야함
//		System.out.println(multi.getOriginalFileName((String)multi.getFileNames().nextElement())); // null
//		System.out.println(multi.getFilesystemName((String)multi.getFileNames().nextElement())); // null
		
		
		return forward;
	}
	
}
