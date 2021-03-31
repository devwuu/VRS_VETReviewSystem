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
	@RequestMapping("hospital_seoul")
	public String hospitalList() {
		
		return "/board/hospital_seoul";

	}
	
	
	//리뷰 게시글 리스트 출력
	@SuppressWarnings("unchecked")
	@RequestMapping("board_seoul")
	public String ReviewList(Model model, @RequestParam("pageNum") String pageNum) {
		
		HashMap<String, Object> map = bs.getBoardList(pageNum);
				
		model.addAttribute("reviewList", (ArrayList<Review>)map.get("reviewList"));
		model.addAttribute("page", (Page)map.get("page"));
		
		return "/board/board_seoul";

	}
	
	
	//리뷰 게시글 내용 보기
	@RequestMapping("board_contview")
	public String reviewContentView(@ModelAttribute("seqno_r") String reviewNo,
									HttpSession session,
									Model model) {
		
		String email = (String)session.getAttribute("sess_id");
		Review r = bs.getReviewContent(reviewNo, email);
		
		model.addAttribute("review", r);
		
		return "/board/board_contview";
	}
	
	
	//리뷰 게시글 검색
	@RequestMapping("reviewSearch")
	public String reviewSearch(@RequestParam("search") String select,
							   @RequestParam("condition") String condition,
							   @RequestParam("pageNum") String pageNum,
							   Model model) {

		
		HashMap<String, Object> map = bs.search(select, condition, pageNum);
		
		model.addAttribute("reviewList", (ArrayList<Review>)map.get("reviewList"));
		model.addAttribute("page", (Page)map.get("page"));
		model.addAttribute("select", select);
		model.addAttribute("condition", condition);
		
		return "/board/board_seoul";
		
	}
	
	
	//리뷰 작성 form 요청
	@RequestMapping("boardRegForm")
	public String reviewRegForm() {
				
		return "/board/board_write";

	}
	
	
	
	//리뷰 작성 PROC
	@RequestMapping("boardRegProc")
	public String reviewRegProc(Review r, MultipartFile fileAttach,
							    HttpSession session) {
		//이진파일은 MultipartFile로 받으면 되고(input name과 같아야함)
		//일반 formfiled는 같은 필드이름을 가진 클래스로 받으면 된다.
		
		String writer = (String)session.getAttribute("sess_id");
		r.setWriter(writer);
		
		String reviewNo = bs.insertBoard(r, fileAttach, session);
		
		return "redirect:board_contview?seqno_r="+reviewNo;
		//mapping 한 url 기준으로 적어줍니다.
		//작성 후, reivew url로 북마킹 할 수 있도록 redirect하되 review No를 get방식으로 가지고 갈 수 있도록 
		//연결하여 리턴합니다.
		
	}

	
	
	//리뷰 수정 FORM 요청
	@RequestMapping("boardModForm")
	public String reviewModForm(Review r, Model model, FileAttached f) {
		
		model.addAttribute("review", r);
		model.addAttribute("file", f);		
		
		return "/board/board_contMod";
	}
	
	
	
	//리뷰 수정 PROC
	@RequestMapping("boardModProc")
	public String reviewModProc(Review r, MultipartFile fileAttach,
								HttpSession session) {
		
		bs.updateBoard(r, fileAttach, session);
		
		return "redirect:board_contview?seqno_r="+r.getReviewNo();
		//작성 후, reivew url로 북마킹 할 수 있도록 redirect하되 review No를 get방식으로 가지고 갈 수 있도록 
		//연결하여 리턴합니다.
	}
	
	
	
	//리뷰 삭제
	@RequestMapping("boardDel")
	public String reviewDel(String reviewNo, HttpSession session, RedirectAttributes redirectAttributes) {
		
		bs.delReview(reviewNo, session);
		
		redirectAttributes.addFlashAttribute("delStat", "1");
		
		return "redirect:board_seoul";
		
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
	
	
	//board게시글리스트 갱신(ajax)
	@RequestMapping("boardListUpdate")
	public ResponseEntity<Page> boardListUpdate(int lastPage, int maxPage) {
	
		Page page = new Page(lastPage+1, maxPage);
		
		return new ResponseEntity<Page>(page, HttpStatus.OK);
	}
	
	//board게시글리스트 갱신(div)
	@RequestMapping("board_page")
	public String board_page(int startPage, int maxPage, Model model) {

		Page page = new Page(startPage, maxPage);
		
		model.addAttribute("page", page);
		
		return "/board/board_page";
	}
	
	
	
	
	
	
}
