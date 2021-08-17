package com.bb.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.bb.user.dto.Code;
import com.bb.user.dto.Member;
import com.bb.user.service.CodeService;
import com.bb.user.service.MemberService;



// 매핑시, member/모시기 가 반복되기 때문에 member를 어노테이션을 통해 따로 빼줄 수 있다.
// 즉 매핑 주소 중에 반복되는 부분을 클래스 위로 @RequestMapping("공통경로") 를 이용하여 빼줄 수 있음

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private CodeService cs;
       

	//로그인
    @RequestMapping("login")
    public String login(Member m, Model model, HttpSession session) {
    	
    	String view = null;
    	String email = m.getEmail();
		String pw = m.getPw();
		
		Map<String, String> map = ms.login(email, pw);
		
		if(map.get("loginStat").equals("1")) {
			//로그인 성공
			
			session.setAttribute("sess_id", email);						
			session.setAttribute("sess_nickname", map.get("nickname"));		
			
			view="redirect:/";

		}else {
			//로그인 실패
			//login_stat = 1 : 로그인 실패
			//login_stat = 2 : 회원가입 완료
			//login_stat = 3 : 비밀번호 변경 완료
			model.addAttribute("login_stat", "1");
			
			view="index";
		}
		
		return view;
    }
    
    
    //로그아웃
    @RequestMapping("logout")
    public String logout(HttpSession session) {
    	
		session.invalidate();
		
		return "redirect:/";
    }
    
    
    //회원가입시 이메일 중복검사
    @RequestMapping("email_check")
    public ResponseEntity<String> emailCheck(@ModelAttribute("email_user") String emailRequest) {
    	//post 방식으로 넘긴 것도 @ModelAttribute로 받을 수 았음.
    	
		String rs = Integer.toString(ms.emailCheck(emailRequest));
	
		return new ResponseEntity<String>(rs, HttpStatus.OK);
		
			
			
		// 다양한 방법
		/*
			1. 
				@ModelAttribute 말고
				@RequestParam을 사용해도 됨. (사용방법은 ModelAttribute와 동일)
				request.getParameter와 동일하다.
				단, @RequestParam의 경우 받을 변수와 보낸 변수의 이름이 같을 경우엔
				어노테이션 생략 후 String 변수이름으로 선언해도 괜찮다.(디폴트가 RequestParam인듯?)
			2. 
				return 타입을 ResponseEntity를 사용해도 괜찮음
				return new ResponseEntity<변수의 타입>(던질 변수의 이름, HttpStatus.OK);
				(가공 없이 바로 return할 것이기 때문에 리턴에서 new로 객체 생성)
				로 retrun 해주면 http의 상태가 ok(정상응답) 일 때 변수를 던져준다.(변수값으로 응답한다)
				ajax로 응답받을 것이기 때문에 상태와 값만 던져줘도 되기 때문에
				이렇게 처리가 가능하다.
				
			3.
				response를 사용하는 방법
				HttpServletResponse response
				PrintWriter out = response.getWriter();
				out.print(ms.emailCheck(emailRequest));
		*/	
			
		
    	
    }
    
    
    //인증 번호 체크
    @RequestMapping("certificaitonCheck")
    public ResponseEntity<String> cerCodeCheck(String cerCode, String email) {
    	
    	int rs = ms.cerCodeCheck(cerCode, email);
    	String result = Integer.toString(rs);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
    //회원가입
    @RequestMapping("memRegProc")
    public String memRedProc(Member member, Model model) {
    	
    	String email = member.getE_id()+"@"+member.getE_domain();
    	member.setEmail(email);
    	
		ms.join(member);
		model.addAttribute("login_stat", "2");
		//login_stat = 1 : 로그인 실패
		//login_stat = 2 : 회원가입 완료
		//login_stat = 3 : 비밀번호 변경 완료
		
		return "index";
    }
    
    
    //회원정보 form 요청
    @RequestMapping("myInfor")
    public String myInfor(HttpSession session, Model model,
    					  @ModelAttribute("mod_stat") String mod_stat) {
    	
    	Member m = ms.getInfor((String)session.getAttribute("sess_id"));
		ArrayList<Code> codeList = cs.getCodeList("관심사");
		
		model.addAttribute("codeList", codeList);
		model.addAttribute("member", m);
		
		model.addAttribute("mod_stat", mod_stat);
		//회원정보 수정 시(redirectAttributes), 수정 결과를 가져옴.
		
		return "member/myInfor";
    	
    }
    
    
    //회원정보 수정
    @RequestMapping("myInforMod")
    public String myInforMod(Member member, RedirectAttributes redirectAttributes) {
    	
    	//입력한 비밀번호가 없을 경우 기존과 동일한 비밀번호로 Update
    	if(member.getPwUpdate().equals("")) {
    		member.setPwUpdate(member.getPw());
    	}
    	
    	
		Code[] interestCode = new Code[10];
		//이게 과연 좋은 생각인지 의문이 듦.
    	
		int i = 0;
		
		if(member.getInterest() != null) {
			for(String s : member.getInterest()) {
				if(s != null) {
					interestCode[i] = new Code();
					interestCode[i].setCategory("관심사");
					interestCode[i].setCodeValue(s);
					i++;
				}
			}
		}
		
    	
    	member.setInterestCode(interestCode);
		
    	Map<String, String> map = ms.update(member);
		
		if(map.get("pwCheck").equals("ok")) {
			//입력한 pw가 DB의 pw와 일치하면
			
			redirectAttributes.addFlashAttribute("mod_stat", "1");
			//redirectAttributes시 전달할 파라미터값 설정
			//mod_stat : 1 == 변경 성공
			//mod_stat : 2 == 변경 실패(탈퇴 요청 실패)
			
		}else {
			//입력한 pw가 DB의 pw와 일치하지 않으면
			
			redirectAttributes.addFlashAttribute("mod_stat", "2");
			//mod_stat : 1 == 변경 성공
			//mod_stat : 2 == 변경 실패(탈퇴 요청 실패)
		}
		
		return "redirect:myInfor";
		//redirect할 때는 매핑할 메서드 이름으로 적어준다.
		//상기의 myInfor 메서드로 이동
    }
    
    
    
    //탈퇴 요청
    @RequestMapping("memDelRequest")
    public String memberDelRequest(Member member,
    							   RedirectAttributes redirectAttributes) {
		
    	String view = null;
		int rs = ms.delMember(member.getEmail(), member.getPw());
		
		if(rs>0) {
			view="redirect:logout";
			
		}else {
			redirectAttributes.addFlashAttribute("mod_stat", "2");
			//mod_stat : 1 == 변경 성공
			//mod_stat : 2 == 변경 실패(탈퇴 요청 실패)

			view="redirect:myInfor";
		}
		
    	return view;
    	
    }
    
    
    //비밀번호 찾기용 email 유효성 체크
    // 이메일 중복 검사와 로직이 비슷해서 어떻게 구성할지 고민중
    @RequestMapping("email_check_pw")
    public ResponseEntity<String> email_check_pw(@ModelAttribute("email_user") String emailRequest) {
    	//post 방식으로 넘긴 것도 @ModelAttribute로 받을 수 았음.
    	
		String rs = Integer.toString(ms.emailCheckPw(emailRequest));
	
		return new ResponseEntity<String>(rs, HttpStatus.OK);
    }
    
    
    
    
    //비밀번호 찾기
    @RequestMapping("memFindProc")
    public String memberFindProc(String email, Model model) {
    	
    	int rs = ms.findMember(email);
    	
    	if(rs > 0) {
    		rs = 3;
    	}
    	
    	model.addAttribute("login_stat", rs);
    	//login_stat = 1 : 로그인 실패
		//login_stat = 2 : 회원가입 완료
		//login_stat = 3 : 비밀번호 변경 완료
 
    	return "index";
    }

    
    //회원추천
    @RequestMapping("userRecommendProc")
    public ResponseEntity<String> userRecommendProc(String sessionId, String recomUser) {
    	
    	int rs = ms.recommendUser(sessionId, recomUser);
    	
    	String rsString = Integer.toString(rs);
    	
    	return new ResponseEntity<>(rsString, HttpStatus.OK);
    }
    
    
    //회원 신고
    @RequestMapping("userReportProc")
    public ResponseEntity<String> userReportProc(String sessionId, String reportUser) {
    	
    	int rs = ms.reportUser(sessionId, reportUser);
    	
    	String rsString = Integer.toString(rs);
    	
    	return new ResponseEntity<>(rsString, HttpStatus.OK);
    }
    
    
	

}
