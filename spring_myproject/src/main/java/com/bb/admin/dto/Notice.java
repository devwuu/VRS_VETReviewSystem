package com.bb.admin.dto;

public class Notice {

	private String noticeNo;
	private String writer;
	private String gradeName;
	private String title;
	private String content;
	private String count;
	private String wdate;
	private String mdate;
	private FileAttached fileAttached;
	
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getnoticeNo() {
		return noticeNo;
	}
	public void setnoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	public FileAttached getFileAttached() {
		return fileAttached;
	}
	public void setFileAttached(FileAttached fileAttached) {
		this.fileAttached = fileAttached;
	}

	
	
	
}
