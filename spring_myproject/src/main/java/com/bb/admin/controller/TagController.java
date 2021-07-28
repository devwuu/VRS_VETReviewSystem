package com.bb.admin.controller;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bb.admin.dto.Code;
import com.bb.admin.service.TagService;
import com.bb.admin.service.TagServiceImpl;

@Controller
@RequestMapping("/tag/")
public class TagController {

	
	@Autowired
	TagService tagService = new TagServiceImpl();
	
	Log log = LogFactory.getLog(TagController.class);
	
	
	@RequestMapping("tagView")
	public String tagView(Model model) {
		
		ArrayList<Code> tagList = tagService.getTagList();
		
		model.addAttribute("tagList", tagList);
		
		return "/hospital/tagList";
	}
	
	
}
