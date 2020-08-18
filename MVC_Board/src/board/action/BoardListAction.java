package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.svc.BoardListService;
import board.vo.ActionForward;
import board.vo.BoardBean;
import board.vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*[1] BoardListService 클래스를 통해 게시물 전체 목록 조회하여 
		 * 조회된 결과를 request 객체 저장 후, qna_board_list.jsp 페이지로 포워딩(디스패치방식)*/
//		System.out.println("BoardListAction!!!");
		ActionForward forward = null;
		
		
		/*[2] 페이징 처리를 위한 변수 선언 */
		int page = 1; // 현재 페이지번호를 저장할 변수
		int limit = 10; // 한 페이지에 표시할 게시물 수
		
		/*[3] 전달된 request 객체의 파라미터 중, 'page'파라미터가 null이 아닌 경우, 
		 * 해당 파라미터 값을 page변수에 저장 */
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page")); // String => int 변환
		}
		
		/* [4] BoardListService 클래스의 인스턴스 생성 */
		BoardListService boardListService = new BoardListService();
		
		/* [5]전체 게시물 수 조회를 위해 BoardListService 인스턴스의 getListCount 메서드 호출
		 *    파라미터: 없음, 리턴타입(int listCount) */
		int listCount = boardListService.getListCount();
		
		/* [6]전체 게시물 목록 조회를 위해 BoardListService 객체의 getArticleList()메서드 호출 */
		// ==> 파라미터: 현재 페이지번호(page), 페이지당 게시물 수(limit)
		//     리턴타입: ArrayList<BoardBean>(articleList)
		
		ArrayList<BoardBean> articleList = boardListService.getArticleList(page, limit);
		
		
		/* [7] 페이지 목록 처리를 위한 계산 */
		// 1. 전체 페이지 수 계산(총 게시물 수 / 페이지당 게시물 수 + 0.95(소수점으로 끝날경우 대비))
		int maxPage = (int)((double)listCount / limit + 0.95);
		
		// 2. 현재 페이지에서 보여줄 시작 페이지 번호(1, 11, 21 등)
		int startPage = (((int)((double)page / 10 + 0.9)) -1) * 10 + 1; 
		
		// 3. 현재 페이지에서 보여줄 끝 페이지 번호(10, 20, 30 등)
		int endPage = startPage + 10 - 1;
		
		// 4. 마지막 페이지가 현재 페이지(page)에서 표시할 최대 페이지 수(전체 페이지 수)보다 클 경우
		//    마지막 페이지 번호(endPage)를 전체 페이지(maxPage) 번호로 교체
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 5. 계산된 페이지 정보들을 PageInfo 객체에 저장
		
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		
		// 6. 페이지 정보 객체(PageInfo)와 게시물 목록 저장된 객체(ArrayList<BoardBean>)를 
		// request객체의 setAttribute() 메서드로 저장;
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		
		/* [8] ActionForward 객체 생성 후, board 폴더의 qna_board_list.jsp 페이지로 포워딩
		 *    ==>서블릿 주소를 유지하고, request 객체를 유지해야하므로 Dispatch 방식 포워딩 */
		forward = new ActionForward();
//		forward.setRedirect(false); // 기본값이므로 생략 가능
		forward.setPath("/board/qna_board_list.jsp");
				
		
		return forward;
	}
	
}
