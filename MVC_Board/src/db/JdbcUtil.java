package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*JDBC 관련 작업 처리 기능을 제공하는 클래스
 * => 모든 메서드를 인스턴스 생성없이 호출 가능하도록 static 으로 선언*/

public class JdbcUtil {
	/* [1] JDBC 연결 후 Connection 객체를 리턴하는 getConnection() 메서드 정의 */
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			/* [1-1] 톰캣 컨텍스트 객체(context.xml) 가져오기 */
			Context initCtx = new InitialContext();
			
			// context.xml 내의 <Resource> 태그 부분 가져오기
//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			
			// context.xml 내의 지정된 name 속성항목을 찾아 DataSource 객체(커넥션풀) 반환
//			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");
			
			/* [1-2] 위의 lookup()메서드 호출을 하나로 결합할 경우 */
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
			
			/* [1-3] 커넥션 풀로부터 Connection 객체 가져오기 */
			con = ds.getConnection(); // context.xml 내에 username, password 입력 시
//			con = ds.getConnection("root", "1234"); // context.xml내에 username, password) 미입력 시
			
			/* [1-4] DB의 트랜잭션 처리를 위해 Auto Commit 기능을 해제 */
			// ==> 작업 성공 시 commit(), 실패 시, rollback() 메서드 수동으로 호출해야 함
			con.setAutoCommit(false);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return con;
	}
	
	/* [2-1] 사용한 Connection 객체를 반환하기 위한 close() 메서드 정의 */
	public static void close(Connection con) {
		if(con != null) try {con.close();}catch(Exception e) {} //비어있을 경우 NullPointException 방지
	}
	
	/* [2-2] 사용한 PreparedStatement 객체를 반환하기 위한 close() 메서드 정의 */
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null) try {pstmt.close();}catch(Exception e) {} //비어있을 경우 NullPointException 방지
	}
	
	/* [2-3] 사용한 ResultSet 객체를 반환하기 위한 close() 메서드 정의 */
	public static void close(ResultSet rs) {
		if(rs != null) try {rs.close();}catch(Exception e) {} //비어있을 경우 NullPointException 방지
	}
	
	/* [3] DB조작 완료 후 commit() 메서드 호출을 위한 commit() 메서드 정의 
	 * ==> 트랜잭션 처리를 위해 Auto Commit 기능을 해제했을 때 필요 */
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* [4] DB조작 실패 시 rollback() 메서드 호출을 위한 rollback() 메서드 정의 
	 * ==> 트랜잭션 처리를 위해 Auto Commit 기능을 해제했을 때 필요 */
	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}







