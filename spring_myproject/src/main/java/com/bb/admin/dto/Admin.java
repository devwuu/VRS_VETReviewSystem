package com.bb.admin.dto;

public class Admin {
	
	private String email;
	private String pw;
	private String nickName;
	private String gradeName;
	private String gradeCode;
	
	//로그인 시 회원 수를 넘기기 위해 사용하는 필드
	private int regMem;
	private int delMem;

	
	
	public int getRegMem() {
		return regMem;
	}
	public void setRegMem(int regMem) {
		this.regMem = regMem;
	}
	public int getDelMem() {
		return delMem;
	}
	public void setDelMem(int delMem) {
		this.delMem = delMem;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
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
