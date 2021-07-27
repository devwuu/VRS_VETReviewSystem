package com.bb.admin.dto;

public class Member {
	
	private String email;
	private String pw;
	private String nickName;
	private String gradeName;
	private String gradeCode;
	private Code interest[];
	private String wdate;
	private String delDate;
	private String isDel;
	
	private String recomCount;
	private String reportCount;
	
	
	
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
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
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
	
	public Code[] getInterest() {
		return interest;
	}
	
	public void setInterest(Code interest[]) {
		this.interest = interest;
	}
	public String getRecomCount() {
		return recomCount;
	}
	public void setRecomCount(String recomCount) {
		this.recomCount = recomCount;
	}
	public String getReportCount() {
		return reportCount;
	}
	public void setReportCount(String reportCount) {
		this.reportCount = reportCount;
	}


	
}
