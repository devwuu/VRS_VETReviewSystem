package com.bb.user.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.*;

import org.springframework.web.multipart.*;

import com.bb.user.dto.Notice;
import com.bb.user.dto.Review;
import com.bb.user.dto.SnsReview;





public interface BoardService {

	//리뷰 등록
	String insertBoard(Review r, MultipartFile fileAttach, HttpSession session);
	
	//리뷰 수정
	String updateBoard(Review r, MultipartFile fileAttach, HttpSession session);
	
	//리뷰 리스트 가져오기
	HashMap<String, Object> getBoardList(String pageNum);
	
	//리뷰 내용 보기
	Review getReviewContent(String no, String email);

	//게시글 삭제
	int delReview(String reviewNo, HttpSession session);

	//북마크 리스트 가져오기
	ArrayList<Review> getBookmarkList(String email);

	//북마크 등록/삭제 기능
	int bookmarkProc(String review_no, String review_url, String email);

	//게시글 검색(제목, 작성자)
	ArrayList<Review> search(String select, String condition);

	//공지사항 리스트 출력
	ArrayList<Notice> getNoticeList();

	//공지사항 내용 보기
	Notice getNoticeContent(String noticeNo);

	//공지사항 5개 출력
	ArrayList<Notice> getNoticeListLimit();

	//sns형태 리뷰 리스트 가져오기
	ArrayList<SnsReview> getSNSList();

	//sns형태 리뷰 등록
	int insertSnsReview(SnsReview sr, HttpSession session, MultipartFile attachFile);

	//sns형태 리뷰 삭제
	int delSnsReview(String snsNo, HttpSession session);

	//sns형태 리뷰 수정
	String updateSnsReview(SnsReview snsReview, MultipartFile attachFileMod, HttpSession session);
	
	
}
