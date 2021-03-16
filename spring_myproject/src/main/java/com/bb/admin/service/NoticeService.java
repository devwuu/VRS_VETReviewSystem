package com.bb.admin.service;

import java.util.ArrayList;

import javax.servlet.http.*;

import org.springframework.web.multipart.*;

import com.bb.admin.dto.Notice;

public interface NoticeService {
	
	
	//공지사항 등록
	int noticeInsert(Notice n, HttpSession session, MultipartFile fileAttach);
	
	//공지사항 수정
	String noticeUpdate(Notice n, HttpSession session, MultipartFile fileAttach);
	
	//공지사항 삭제
	int noticeDelete(String noticeNo, HttpSession session);
	
	//공지사항 리스트
	ArrayList<Notice> getNoticeList();

	//공지사항 내용 보기
	Notice getNoticeContent(String no);

}
