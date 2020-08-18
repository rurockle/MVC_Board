package member.svc;
import static member.db.JdbcUtil.*;

import java.sql.Connection;

import member.dao.MemberDAO;
import member.vo.MemberBean;

public class MemberLoginProService {

	/* [1] 전달받은 id, passwd를 사용하여 로그인 작업을 수행하는 loginMember9)메서드 정의 */
	public int loginMember(MemberBean member) {
		
		Connection con = getConnection();
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		memberDAO.setConnection(con);
		
		/* [2] MemberDAO 클래스의 loginMember() 메서드를 호출하여 아이디, 패스워드 가능 여부 판별 */
		int loginResult = memberDAO.loginMember(member);
		
		
		close(con);
		
		return loginResult;
	}

}
