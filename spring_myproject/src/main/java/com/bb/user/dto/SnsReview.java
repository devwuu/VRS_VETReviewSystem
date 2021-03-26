package com.bb.user.dto;

public class SnsReview {

	private String snsReviewNo;
	private String snsContent;
	private String email;
	private String wdate;
	private FileAttached fileAttached;
	
	//filename을 가져오기 위함
	private String fileName;
	
	
	public String getSnsReviewNo() {
		return snsReviewNo;
	}
	public void setSnsReviewNo(String snsReviewNo) {
		this.snsReviewNo = snsReviewNo;
	}
	public String getSnsContent() {
		return snsContent;
	}
	public void setSnsContent(String snsContent) {
		this.snsContent = snsContent;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public FileAttached getFileAttached() {
		return fileAttached;
	}
	public void setFileAttached(FileAttached fileAttached) {
		this.fileAttached = fileAttached;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	
}
