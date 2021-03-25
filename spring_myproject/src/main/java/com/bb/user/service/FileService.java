package com.bb.user.service;

import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.*;

import com.bb.user.dto.*;



public interface FileService {

	//첨부파일 객체 리턴(폼에서 가져온 정보를 담아서 리턴)
	FileAttached getInstanceOf(FileAttached f, MultipartFile fileAttach, HttpSession session);
	
	//첨부 파일 삭제(게시글 수정을 통해 ajax로 요청)
	int delFile(String reviewNo, HttpSession session);

	//첨부파일 삭제(sns게시글 수정form / ajax)
	String delSnsFile(String snsReviewNo, HttpSession session);
	
}
