package com.bb.admin.controller;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bb.admin.dto.Code;
import com.bb.admin.service.TagService;
import com.bb.admin.service.TagServiceImpl;

@Controller
@RequestMapping("/tag/")
public class TagController {

	
	@Autowired
	TagService tagService = new TagServiceImpl();
	
	Log log = LogFactory.getLog(TagController.class);
	
	
	//태그 리스트 출력
	@RequestMapping("tagView")
	public String tagView(Model model, @ModelAttribute("codeResult") String codeResult) {
		
		ArrayList<Code> tagList = tagService.getTagList();
		
		model.addAttribute("tagList", tagList);
		model.addAttribute("codeResult", codeResult);
		
		return "/hospital/tagList";
	}
	
	
	//태그 value 중복 검사
	@RequestMapping("tagValueCheck")
	public ResponseEntity<String> tagValueCheck(String checkValue) {
		 
		int rs = tagService.checkTagValue(checkValue);
		String rsString = Integer.toString(rs);
		
		return new ResponseEntity<String>(rsString, HttpStatus.OK);
	}
	
	
	//태그 등록
	@RequestMapping("tagReg")
	public String tagReg(Code code, RedirectAttributes rd) {
		
		int rs = tagService.insertTag(code);
		
		rd.addFlashAttribute("codeResult",rs);
		
		return "redirect:/admin/tag/tagView";
	}
	
	
	//태그 삭제
	@RequestMapping("tagDel")
	public String tagDel(String[] codeValue, RedirectAttributes rd) {
		
		int rs = tagService.deleteTag(codeValue);
		
		if(rs>0) {
			// codeResult = 1 : 코드 등록 완료
			// codeResult = 2 : 코드 삭제 완료
			rd.addFlashAttribute("codeResult", 2);
		}
		
		return "redirect:/admin/tag/tagView";
	}
	
}
