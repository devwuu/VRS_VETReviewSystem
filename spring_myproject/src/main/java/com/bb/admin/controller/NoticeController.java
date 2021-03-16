package com.bb.admin.controller;


import java.util.ArrayList;
import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.support.*;

import com.bb.admin.dto.*;
import com.bb.admin.service.NoticeService;

@Controller
public class NoticeController{

	
	@Autowired
	private NoticeService ns; 
	
	
	
	//공지사항 리스트 출력
	@RequestMapping("noticeList")
	public String noticeList(Model model, @ModelAttribute("delStat") String delStat) {
		
		ArrayList<Notice> NoticeList = ns.getNoticeList();
		model.addAttribute("NoticeList", NoticeList);
		model.addAttribute("delStat", delStat);
		
		return "/notice/noticeList";		
		
	}
	
	
	
	//공지사항 내용 조회
	@RequestMapping("noticeView")
	public String noticeView(@ModelAttribute("noticeNo") String noticeNo,
							 Model model) {
		
		Notice n = ns.getNoticeContent(noticeNo);
		model.addAttribute("notice", n);
		
		return "/notice/notice_contView";
		
	}
	
	
	
	//공지사항 작성 FORM 요청
	@RequestMapping("noticeWrite")
	public String noticeWrite() {
		
		return "/notice/notice_write";
	}
	
	
	
	//공지사항 작성 PROC
	@RequestMapping("noticeWriteProc")
	public String noticeWriteProc(Notice n, RedirectAttributes redirectAttribute,
								  HttpSession session, MultipartFile fileAttach) {
		
		
		int noticeNo = ns.noticeInsert(n, session, fileAttach);
		
		redirectAttribute.addFlashAttribute("noticeNo", noticeNo);
		
		return "redirect:noticeView";
	}
	
	
	
	//공지사항 수정 Form 요청
	@RequestMapping("noticeModForm")
	public String noticeModForm(Notice n, FileAttached f, Model model) {

		model.addAttribute("notice", n);
		model.addAttribute("file", f);
		
		return "/notice/notice_contMod";
	}
	
	
	
	//공지사항 수정 PROC
	@RequestMapping("noticeModProc")
	public String noticeModProc(Notice n, MultipartFile fileAttach, HttpSession session,
								RedirectAttributes redirectAttribute) {
		
		ns.noticeUpdate(n, session, fileAttach);
		redirectAttribute.addFlashAttribute("noticeNo",n.getnoticeNo());
		
		return "redirect:noticeView";
	}
	
	
	
	//공지사항 삭제
	@RequestMapping("noticeDel")
	public String noticeDel(String noticeNo, HttpSession session,
							RedirectAttributes redirectAttribute) {
		
		int rs = ns.noticeDelete(noticeNo, session);
		redirectAttribute.addFlashAttribute("delStat", rs);
		
		return "redirect:noticeList";
		
	}
	
	
}
