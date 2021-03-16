package com.bb.user.dto;

public class Member {
	
	private String email;
	private String pw;
	private String nickName;
	private String grade;
	private Code interestCode[];
	
	private String interest[];
	private String e_id;
	private String e_domain;
	
	
	
	
	
	
	public Code[] getInterestCode() {
		return interestCode;
	}
	public void setInterestCode(Code[] interestCode) {
		this.interestCode = interestCode;
	}
	public String[] getInterest() {
		return interest;
	}
	public void setInterest(String[] interest) {
		this.interest = interest;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getE_domain() {
		return e_domain;
	}
	public void setE_domain(String e_domain) {
		this.e_domain = e_domain;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	


	
}
