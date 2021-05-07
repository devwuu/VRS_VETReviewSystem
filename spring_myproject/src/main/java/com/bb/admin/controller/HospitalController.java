package com.bb.admin.controller;


import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bb.admin.dto.Code;
import com.bb.admin.dto.Hospital;
import com.bb.admin.service.HospitalService;
import com.bb.admin.service.HospitalServiceImpl;

@Controller
@RequestMapping("/hospital/")
public class HospitalController{
	
	private Logger log = LoggerFactory.getLogger(HospitalController.class);
	private HospitalService hs = new HospitalServiceImpl();
	
	//병원 목록 출력
   @RequestMapping("hospitalView")
   public void hospitalView(Model model, @ModelAttribute("regRs") String rs) {

	   HashMap<String, Object> result = hs.getHospitalList();
	   ArrayList<Hospital> hospitalList = (ArrayList<Hospital>)result.get("HospitalList");
	   ArrayList<Code> codeList = (ArrayList<Code>)result.get("codeList");
	   
	   model.addAttribute("hospitalList", hospitalList);
	   model.addAttribute("codeList", codeList);
	   model.addAttribute("rs", rs);
   }
   
   //병원 등록 proc
   @RequestMapping("regProc")
   public String regProc(Hospital h, RedirectAttributes ra) {
	   
	   int rs = hs.insertHospital(h);
	   ra.addFlashAttribute("regRs", rs);
	   
	   return "redirect:/admin/hospital/hospitalView";
   }
   
   //병원 수정 Proc
   @RequestMapping("modProc")
   public String hosModProc(Hospital h, RedirectAttributes ra) {
	   
	   int rs = hs.updateHospital(h);
	   
	   if(rs>=1) {
		   rs = 2;
	   }
	   
	   ra.addFlashAttribute("regRs", rs);
	   
	   return "redirect:/admin/hospital/hospitalView";
   }
   
	

}
