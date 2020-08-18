package member.svc;

import member.dao.MemberDAO;
import member.vo.MemberBean;
import static member.db.JdbcUtil.*;

import java.sql.Connection;

public class MemberJoinProService {

	// 회원 중복 여부 확인 요청 작업을 수행하기 위한 dupCheckMember() 메서드 정의
	public int dupCheckMember(MemberBean member) {
		Connection con = getConnection();
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		memberDAO.setConnection(con);
		
		int checkResult = memberDAO.dupCheckMember(member);
		
		close(con);
		
		return checkResult;
	}
	
	// 회원 가입(INSERT) 요청 작업을 수행하기 위한 joinMember() 메서드 정의
	public boolean joinMember(MemberBean member) {
		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		memberDAO.setConnection(con);
		
		int insertCount = memberDAO.insertMember(member);
		
		if(insertCount > 0) {
			commit(con);
			isJoinSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isJoinSuccess;
	}


}














