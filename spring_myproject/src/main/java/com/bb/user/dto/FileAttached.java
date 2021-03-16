package com.bb.user.dto;

public class FileAttached {
	
	private String fileNo;
	private String fileName;
	private String fileNameSave;
	private String fileSize;
	private String fileType;
	private String filePath;
	private String wdate;
	
	//리뷰의 첨부파일일 경우 사용
	private String reviewNo;
	
	//공지사항의 첨부파일일 경우 사용
	private String noticeNo;
	
	
	
	public String getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileNameSave() {
		return fileNameSave;
	}
	public void setFileNameSave(String fileNameSave) {
		this.fileNameSave = fileNameSave;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	
	
	
	
}
