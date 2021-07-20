package com.bb.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.support.*;

import com.bb.user.dto.*;
import com.bb.user.service.BoardService;
import com.bb.user.service.BoardServiceImpl;



@Controller
@RequestMapping("/board/")
public class BoardController {

	private final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService bs;

	
	//index페이지에 공지사항 출력
	@RequestMapping("noticeListIndex")
	public String noticeListIndex(Model model) {
	
		ArrayList<Notice> noticeList = bs.getNoticeListLimit();
		
		if(noticeList.size() == 0) {
			//등록된 공지사항이 없을 경우 무한루프 방지를 위해 공지사항 없음을 추가로 안내
			
			Notice notFound = new Notice();
			notFound.setTitle("등록된 공지사항이 없습니다.");
			notFound.setGradeName("-");
			notFound.setCount("-");
			notFound.setWdate("-");
			
			noticeList.add(notFound);
		}
		
		model.addAttribute("noticeList", noticeList);
		
		return "index";
		
	}
	
	
	//공지사항 전체 리스트 보기
	@RequestMapping("noticeList")
	public String noticeList(Model model) {
		
		ArrayList<Notice> noticeList = bs.getNoticeList();
		
		model.addAttribute("noticeList", noticeList);
		
		return "/board/noticeList";		
	}
	
	
	//공지사항 세부 내용 보기
	@RequestMapping("notice_contView")
	public String noticeContentView(@RequestParam("noticeNo") String noticeNo,
								  Model model) {
		
		Notice n = bs.getNoticeContent(noticeNo);
		
		model.addAttribute("notice", n);
		
		return "/board/notice_contView";
		
	}
	
	
	//북마크 리스트 출력
	@RequestMapping("bookmarkList")
	public String bookmarkList(HttpSession session,
							   Model model) {
		
		String email = (String)session.getAttribute("sess_id");
		ArrayList<Review> bookMarkList = bs.getBookmarkList(email);
		
		model.addAttribute("bookmark", bookMarkList);
		
		return "/board/bookmarkList";
		
	}
	
	
	//북마크 등록/삭제
	@RequestMapping("bookmarkProc")
	public ResponseEntity<String> bookmarkProc(@RequestParam("seqno_r") String reviewNo,
											   @RequestParam("url_r") String reviewURL,
											   HttpSession session){
		
		
		String email = (String)session.getAttribute("sess_id");
		
		String rs = Integer.toString(bs.bookmarkProc(reviewNo, reviewURL, email));
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
		
	}

	
	//병원 리스트 출력
	@RequestMapping("hospitalList")
	public String hospitalList(String location, Model model) {

		
		ArrayList<Hospital> hospitalList = bs.getHospitalList(location);
		
		model.addAttribute("hospitalList", hospitalList);
		
		return "/board/hospitalList";

	}
	
	
	//리뷰 게시글 리스트 출력
	@SuppressWarnings("unchecked")
	@RequestMapping("boardReview")
	public String ReviewList(Model model, @ModelAttribute("pageNum") String pageNum, Hospital hospital) {
	
		
		HashMap<String, Object> map = bs.getBoardList(hospital.getHospitalNo(), pageNum);
				
		model.addAttribute("reviewList", (ArrayList<Review>)map.get("reviewList"));
		model.addAttribute("page", (Page)map.get("page"));
		
		Page p = (Page)map.get("page");
			
		model.addAttribute("hospital", hospital);
		
		return "/board/boardReview";

	}
	
	
	//리뷰 게시글 내용 보기
	@RequestMapping("board_contview")
	public String reviewContentView(@ModelAttribute("seqno_r") String reviewNo,
									@ModelAttribute("pageNum") String pageNum,
									Hospital hospital,
									HttpSession session,
									Model model) {
		
		String email = (String)session.getAttribute("sess_id");
		Review r = bs.getReviewContent(reviewNo, email);
		
		model.addAttribute("review", r);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("Hospital", hospital);
		
		return "/board/board_contview";
	}
	
	
	//리뷰 게시글 검색
	@SuppressWarnings("unchecked")
	@RequestMapping("reviewSearch")
	public String reviewSearch(@RequestParam("search") String select,
							   @RequestParam("condition") String condition,
							   @RequestParam("pageNum") String pageNum,
							   Hospital hospital,
							   Model model) {

		
		HashMap<String, Object> map = bs.search(select, condition, pageNum, hospital.getHospitalNo());
		
		model.addAttribute("reviewList", (ArrayList<Review>)map.get("reviewList"));
		model.addAttribute("page", (Page)map.get("page"));
		model.addAttribute("select", select);
		model.addAttribute("condition", condition);
		model.addAttribute("hospital", hospital);
		
		return "/board/boardReview";
		
	}
	
	
	//리뷰 작성 form 요청
	@RequestMapping("boardRegForm")
	public String reviewRegForm(Hospital hospital, String pageNum, Model model) {
		
		model.addAttribute("hospital", hospital);
		model.addAttribute("pageNum", pageNum);
		
		return "/board/board_write";

	}
	
	
	
	//리뷰 작성 PROC
	@RequestMapping("boardRegProc")
	public String reviewRegProc(Review r, MultipartFile fileAttach, Hospital hospital,
							    HttpSession session, RedirectAttributes ra) {
		//이진파일은 MultipartFile로 받으면 되고(input name과 같아야함)
		//일반 formfiled는 같은 필드이름을 가진 클래스로 받으면 된다.
		
		String writer = (String)session.getAttribute("sess_id");
		r.setWriter(writer);
		
		String reviewNo = bs.insertBoard(r, fileAttach, session);
		
		ra.addFlashAttribute("hospital", hospital);
		ra.addFlashAttribute("pageNum", "1");
		ra.addFlashAttribute("seqno_r", reviewNo);
		
		return "redirect:board_contview";
		
		
	}

	
	
	//리뷰 수정 FORM 요청
	@RequestMapping("boardModForm")
	public String reviewModForm(Review r, Model model, FileAttached f, Hospital hospital,
								@RequestParam("pageNum") String pageNum) {
		
		model.addAttribute("review", r);
		model.addAttribute("file", f);		
		model.addAttribute("hospital", hospital);
		model.addAttribute("pageNum", pageNum);
		
		return "/board/board_contMod";
	}
	
	
	
	//리뷰 수정 PROC
	@RequestMapping("boardModProc")
	public String reviewModProc(Review r, MultipartFile fileAttach, Hospital hospital, RedirectAttributes ra,
								HttpSession session, @RequestParam("pageNum") String pageNum) {
			
		
		bs.updateBoard(r, fileAttach, session);
		
		ra.addFlashAttribute("hospital", hospital);
		ra.addFlashAttribute("pageNum", pageNum);
		ra.addFlashAttribute("seqno_r", r.getReviewNo());
		
		
		return "redirect:board_contview";
	}
	
	
	
	//리뷰 삭제
	@RequestMapping("boardDel")
	public String reviewDel(String reviewNo, HttpSession session, RedirectAttributes redirectAttributes,
							Hospital hospital, String pageNum) {
		
		bs.delReview(reviewNo, session);
		
		
		redirectAttributes.addFlashAttribute("delStat", "1");
		redirectAttributes.addFlashAttribute("pageNum", pageNum);
		redirectAttributes.addFlashAttribute("hospital", hospital);
		
		return "redirect:boardReview";
		
	}
	
	
	//sns형 게시판으로 이동
	@RequestMapping("sns_seoul")
	public String snsBoard(Model model, @ModelAttribute("delStat") String delStat) {
		
		ArrayList<SnsReview> snsList = bs.getSNSList();
		
		model.addAttribute("snsList", snsList);
		model.addAttribute("delStat", delStat);
		
		return "/board/sns_seoul";
	}
	
	
	//sns형 게시판 리뷰 등록
	@RequestMapping("snsRegProc")
	public String snsRegProc(SnsReview sr, HttpSession session, MultipartFile attachFile) {
		 
		sr.setEmail((String)session.getAttribute("sess_id"));
		int rs = bs.insertSnsReview(sr, session, attachFile);
		
		if(rs<=0) {
			log.info("insert Error");
		}
		
		return "redirect:sns_seoul";
	}
	
	
	//sns형 게시판 게시글 삭제
	@RequestMapping("snsDel")
	public String snsDel(String snsNo, HttpSession session, RedirectAttributes redirectAttribute){

		int rs = bs.delSnsReview(snsNo, session);
		redirectAttribute.addFlashAttribute("delStat", rs);
		
		return "redirect:/board/sns_seoul";
	}
		
	
	//sns형 게시글 수정
	@RequestMapping("snsUpdate")
	public ResponseEntity<String> snsUpdate(SnsReview snsReview, MultipartFile attachFileMod,
						  HttpSession session) {

		//formdata안의 input name과 필드 이름이 동일한 dto로 받아준다.
		//file은 multipart타입으로 받아준다.

		String rs = bs.updateSnsReview(snsReview, attachFileMod, session);
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
	}
	

	
	
	
	
}
