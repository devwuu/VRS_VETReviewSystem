package com.bb.admin.controller;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bb.admin.dto.Code;
import com.bb.admin.dto.Hospital;
import com.bb.admin.service.HospitalService;
import com.bb.admin.service.HospitalServiceImpl;

@Controller
@RequestMapping("/hospital/")
public class HospitalController{
	
	private Logger log = LoggerFactory.getLogger(HospitalController.class);
	private HospitalService hs = new HospitalServiceImpl();
	
   @RequestMapping("hospitalView")
   public void hospitalView(Model model) {
	   
	   ArrayList<Hospital> hospitalList = hs.getHospitalList();
	  
	   model.addAttribute("hospitalList", hospitalList);
   }
   
   @RequestMapping("hospitalRegForm")
   public void hosRegForm() {
	   
   }
   
	

}
