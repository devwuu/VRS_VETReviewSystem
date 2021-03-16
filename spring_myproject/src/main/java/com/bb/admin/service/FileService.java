package com.bb.admin.service;

import javax.servlet.http.*;

import org.springframework.web.multipart.*;

import com.bb.admin.dto.FileAttached;

public interface FileService {


	//공지사항 첨부 파일 삭제
	String  delFile(String noticeNo, HttpSession session);
	
	//파일 객체 리턴(폼의 정보를 담은 객체임)
	FileAttached instanceOf(FileAttached f, HttpSession session, MultipartFile fileAttach);
	
}
