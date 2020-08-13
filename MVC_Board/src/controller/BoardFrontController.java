package controller;

import java.io.IOException;
import java.net.InterfaceAddress;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardDetailAction;
import action.BoardListAction;
import action.BoardModifyFormAction;
import action.BoardModifyProAction;
import action.BoardReplyFormAction;
import action.BoardWriteProAction;
import vo.ActionForward;



// URL에 요청된 서블릿 주소가 XXX.bo로 끝날 경우
// 해당 주소를 서블릿 클래스인 BoardFrontController 클래스로 연결(URL 매핑) 
// 파라미터 여부는 상관 없음
@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
	// 서블릿 클래스 정의 시 HttpServlet 클래스를 상속받아 정의
	// GET 방식 요청, POST 방식 요청에 해당하는 doGet(), doPost() 메서드 오버라이딩
	// ==> 두 방식을 공통으로 처리하기 위해 doProcess()메서드를 별도로 정의하여 호출
   
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		SYSTEM.OUT.PRINTLN("BOARD프론트CONTROLLER");
		
		// POST 방식 요청에 대한 한글 처리
		request.setCharacterEncoding("UTF-8");
		
		// 요청 URL에 대한 판별
//		String requestURI = request.getRequestURI();
//		System.out.println("Requested URI : "+requestURI); // 1. 프로젝트명 + 파일명 추출
//		
//		String contextPath = request.getContextPath();
//		System.out.println("Context Path: "+ contextPath); // 2. 프로젝트명 추출
//		
//		String command = requestURI.substring(contextPath.length());
//		System.out.println("Command : "+command); // 프로젝트명 문자열 길이에 해당하는 부분부터 마지막까지 추출
		//========================예전에 사용하던 방법
		// 위의 1~3과정을 결합한 메서드 : getServletPath();
		String command = request.getServletPath();
		System.out.println("Command : "+command);  // Command : /Main.bo (서블릿 주소부만 추출)

		
		// 여러 객체(XXXAction)를 동일한 타입으로 다루기 위한 변수 선언
		Action action = null;
		ActionForward forward = null;
		
		// 추출된 서블릿 주소에 따라 각각 다른 작업을 수행하도록 제어
		if (command.equals("/BoardWriteForm.bo")) { /* ( ) 안에는 서블릿 주소*/			
			// 글쓰기 폼을 위한 View 페이지(JSP) 요청은 
			// 별도의 비즈니스 로직(Model) 없이 JSP 페이지로 바로 연결
			// ==> 이 때, JSP 페이지의 URL이 노출되지 않아야하며
			//     또한, request 객체가 유지되어야 하므로 Dispatch 방식으로 포워딩
			// 따라서, ActionForward 객체를 생성하고 URL 전달 및 포워딩 타입 false로 지정
			forward = new ActionForward();
			forward.setPath("/board/qna_board_write.jsp"); /* JSP페이지 이동시 사용 */
//			forward.setRedirect(false); // 기본값이 false이므로 생략 가능	
		}else if(command.equals("/BoardWritePro.bo")) {
			// 글쓰기 비즈니스 로직을 위한 Action 클래스 인스턴스 생성
			// ==> BoardWriteProAction 클래스 인스턴스 생성 및 공통 메서드 execute()호출
			// ==> 로직 수행 후, ActionForward 객체를 리턴받아 포워딩 작업 수행
			action = new BoardWriteProAction(); // <<- 이동할 클래스(Action 인터페이스 상속 필요!)
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace(); /* bo 페이지 이동시 사용*/
			}
		}else if(command.equals("/BoardList.bo")) {
			// 글목록 요청 비즈니스 로직을 위한 Action 클래스 인스턴스 생성
			// ==> BoardListAction 클래스의 인스턴스 생성 및 공통 메서드 execute() 호출
			// ==> 로직 수행 후 ActionForward 객체를 리턴받아 포워딩 작업 수행
			action = new BoardListAction();
			
			try {
				forward=action.execute(request, response); // forward가 있어야 아래 포워딩 작업 가능함
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/BoardDetail.bo")) {
			// 글 상세 내용 요청 비즈니스 로직을 위한 Action 클래스 인스턴스 생성
			// ==> BoardDetailAction 클래스의 인스턴스 생성 및 공통 메서드 execute() 호출
			// ==> 로직 수행 후 ActionForward 객체를 리턴받아 포워딩 작업 수행
			action = new BoardDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardModifyForm.bo")) {
			action = new BoardModifyFormAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardModifyPro.bo")){
			action = new BoardModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if(command.equals("/BoardReplyForm.bo")) {
			action = new BoardReplyFormAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//===============================================================================
		// Redirect 방식과 Dispatch 방식에 대한 포워딩(이동)을 처리하기 위한 영역
		/*1. ActionForward 객체가 null이 아닐 때만 포워딩 작업을 수행하기*/		
		if(forward != null) {
			/* 2. ActionForward 객체가 null이 아닐 때, Redirect 방식여부 판별 */
			if(forward.isRedirect()) {//Redirect 방식일 경우
				// response 객체의 sendRedirect() 메서드 호출하여
				// ActionForward 객체에 저장되어 있는 URL정보전달
				response.sendRedirect(forward.getPath());
			}else{ // Dispatch 방식일 경우
				/* 1) RequestDispatcher 객체를 리턴받기 위해 */
				// request 객체의 getRequestDispatcher() 메서드를 호출
				// ==> 파라미터로 ActionForward 객체에 저장되어 있는 URL 정보 전달
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				
				/* 2) RequestDispatcher 객체의 forward() 메서드를 호출하여 */
				//    전달된 URL로 포워딩
				//  ==> 파라미터로 request 객체와 response 객체 전달
				dispatcher.forward(request, response); 
				// dispatch 방식으로 이동하는 동안 session과 비슷
				
			}
		}
		
		//===============================================================================
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET 방식 요청에 의해 doGet()메서드가 호출되면 doProcess() 메서드를 호출하여
		// request 객체와 response 객체를 파라미터로 전달
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST 방식 요청에 의해 doPost()메서드가 호출되면 doProcess() 메서드를 호출하여
		// request 객체와 response 객체를 파라미터로 전달
		doProcess(request, response);
	}
	
}
