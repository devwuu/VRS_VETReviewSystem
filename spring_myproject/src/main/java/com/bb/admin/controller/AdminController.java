package com.bb.admin.controller;


import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bb.admin.dto.Admin;
import com.bb.admin.dto.Member;
import com.bb.admin.service.AdminService;



@Controller
public class AdminController {

	
	@Autowired
	private AdminService as;
	
	Log log = LogFactory.getLog(AdminController.class);
	
	//로그인
	@RequestMapping("adminLogin")
	public String adminLogin(String email, String pw, HttpSession session, Model model) {
						//requestParam의 변수 이름과 java에서 사용할 변수이름이 같으면
						//어노테이션을 생략하고도 param을 가져올 수 있다.
		
		//servlet 설정에서 기본 경로(루트경로)가 /admin/*
		//로 되어있기 때문에
		// /admin/뒤에 올 경로만 적어줘야 매핑이 된다.
		// admin/adminLogin 으로 할 경우
		// admin/admin/adminLogin으로 잡혀서 매핑이 안됨......
		
		String view = null;
	
		Admin a = as.login(email, pw);
		
		if(a.getGradeName() != null) {
		
			session.setAttribute("sess_id", email);
			session.setAttribute("sess_nickname", a.getNickName());
			session.setAttribute("gradeName", a.getGradeName());
			session.setAttribute("regMem", a.getRegMem());
			session.setAttribute("delMem", a.getDelMem());
		
			view = "main_admin";
			
		}else {					
			model.addAttribute("stat", "1");
			
			view="index_admin";
		}
		
		return view;
		
	}
	
	
	//로그아웃
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return "redirect:/admin/main";
	}
	
	
	//main 화면으로 이동
	@RequestMapping("main_admin")
	public String index() {
		
		return "main_admin";
	}
	
	
	//회원리스트 출력
	@RequestMapping("memList")
	public String memList(Model model) {
		
		ArrayList<Member> memberList= as.getMemberList();
		
		model.addAttribute("memberList", memberList);
		
		return "/member/memList";

	}
	
	
	//회원 검색
	@RequestMapping("memberSearch")
	public String memberSearch(@RequestParam("grade") String gradeCode,
							   @RequestParam("condition") String condition,
							   Model model) {
		
		ArrayList<Member> memberList = as.getSearchMember(gradeCode, condition);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("gradeCode", gradeCode);
		model.addAttribute("condition", condition);
		
		return "/member/memList";
		
	}
	
	
	//탈퇴회원리스트 출력
	@RequestMapping("delMemList")
	public String delMemList(Model model, @ModelAttribute("delState") String delState,
										  @ModelAttribute("condition") String condition) {
		
		ArrayList<Member> memberList = as.getDelMemberList();
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("delState", delState);
		model.addAttribute("condition", condition);
		
		return "/member/delMemList";
		
	}
	
	
	//탈퇴회원 검색
	@RequestMapping("delMemberSearch")
	public String delMemberSearch(String condition, Model model) {
		
		ArrayList<Member> memberList = as.getSearchDelMember(condition);
		model.addAttribute("memberList", memberList);
		model.addAttribute("condition", condition);

		return "/member/delMemList";
	}
	
	
	//회원탈퇴처리
	@RequestMapping("delMemnerReq")
	public String delMemnerReq(String[] selectEmail, RedirectAttributes redirectAttributes, String condition) {
		
		int rs = as.delMember(selectEmail);
		
		redirectAttributes.addFlashAttribute("delState", rs);
		redirectAttributes.addFlashAttribute("condition", condition);
		
		return "redirect:/admin/delMemList";
	}
	
	

}
