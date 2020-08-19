package member.svc;

import static board.db.JdbcUtil.close;
import static board.db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import board.dao.BoardDAO;
import board.vo.BoardBean;
import member.dao.MemberDAO;
import member.vo.MemberBean;

public class MemberListService {

	public ArrayList<MemberBean> getMemberList() {
		
		
		/* [2-2] JdbcUtil 클래스의 getConnection()메서드를 호출하여 Connection 객체 가져오기 */

		Connection con = getConnection();
		/* [2-3] MemberDAO 클래스의 getInstance() 메서드를 호출하여MemberDAO객체 가져오기 */
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		/* [2-4] MemberDAO 클래스의 setConnection() 메서드를 호출하여 MemberDAO객체 가져오기 */
		memberDAO.setConnection(con);
		
		/* [2-5] MemberDAO 클래스의 selectMemberList()메서드를 호출하여 전체 회원 목록 조회 */
		// 파라미터: 없음 | 리턴타입: ArrayList<MemberBean>(memberList)
		ArrayList<MemberBean> memberList  = memberDAO.selectMemberList();
		
		/* [2-6] close()메서드를 호출하여 Connection 객체 반환 */

		close(con);
		
		return memberList;
		
	}

	public ArrayList<MemberBean> getMemberList(String orderTarget, String orderType) {
		
		
		/* [2-2] JdbcUtil 클래스의 getConnection()메서드를 호출하여 Connection 객체 가져오기 */

		Connection con = getConnection();
		/* [2-3] MemberDAO 클래스의 getInstance() 메서드를 호출하여MemberDAO객체 가져오기 */
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		/* [2-4] MemberDAO 클래스의 setConnection() 메서드를 호출하여 MemberDAO객체 가져오기 */
		memberDAO.setConnection(con);
		
		/* [2-5] MemberDAO 클래스의 selectMemberList()메서드를 호출하여 전체 회원 목록 조회 */
		// 파라미터: 정렬 대상(orderTarget), 정렬 방식(orderType) | 리턴타입: ArrayList<MemberBean>(memberList)
		ArrayList<MemberBean> memberList  = memberDAO.selectMemberList(orderTarget, orderType);
		
		/* [2-6] close()메서드를 호출하여 Connection 객체 반환 */

		close(con);
		
		return memberList;
		
	}
	
	
}
