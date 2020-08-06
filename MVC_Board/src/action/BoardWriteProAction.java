package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// MultipartRequest multi = new MultipartRequest(request,업로드경로,업로드파일크기,한글처리,동일이름변경);
		// 2] 업로드 경로: 웹서버에 upload 폴더만들기 
		//(프로젝트 파일자체가 웹서버에 업로드 되므로, WebContent 안에 upload폴더 만들면 됨)
		// 3]  upload 폴더에 물리적인 경로 넣기(request객체의 getRealPath 내장객체를 사용함)
		String uploadPath = request.getServletContext().getRealPath("/upload");
		// 콘솔창에 물리적 경로 출력
//		System.out.println(uploadPath);

		// 4] 업로드 파일 크기: 10M 
		int maxSize = 10*1024*1024;

		// 5] 한글처리:  "utf-8" 입력

		// 6] 동일이름 변경: DefaultFileRenamePolicy() 객체 생성후(new), 사용
		MultipartRequest multi = new MultipartRequest(request,uploadPath,maxSize,"utf-8",new DefaultFileRenamePolicy());

		// 7] 310 페이지 MultipartRequest 메서드의 파라미터 가져오는 request내장객체 이용

		// name pass subject content 파라미터 가져오기
		String name=multi.getParameter("BOARD_NAME");
		String pass=multi.getParameter("BOARD_PASS");
		String subject=multi.getParameter("BOARD_SUBJECT");
		String content=multi.getParameter("BOARD_CONTENT");
		
		// 8] file 업로드된 파일 이름 가져오기 
		// + DTO객체에 file 변수, setter/getter 추가 + set메서드를 이용해서 파일이름 저장
		String file=multi.getFilesystemName("BOARD_FILE");
		
		System.out.println(name+pass+subject+content+file);
		return null;
	}
	
}
