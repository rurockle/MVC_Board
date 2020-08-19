package member.vo;

import java.util.Date;

/*
 * member 테이블
CREATE TABLE member (
	idx INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(10) NOT NULL,
	id VARCHAR(12) UNIQUE NOT NULL,
	passwd VARCHAR(16) NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	regDate DATE NOT NULL
);
 */
public class MemberBean {
	private int idx;
	private String name;
	private String id;
	private String passwd;
	private String email;
	private Date regDate;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "MemberBean [idx=" + idx + ", name=" + name + ", id=" + id + ", passwd=" + passwd + ", email=" + email
				+ ", regDate=" + regDate + "]";
	}
	
}
