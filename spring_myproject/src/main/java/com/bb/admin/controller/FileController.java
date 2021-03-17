package com.bb.admin.controller;


import java.io.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.bb.admin.service.FileService;

@Controller
public class FileController{
	
	@Autowired
	private FileService fs;
  
   
	//첨부파일 삭제
	@RequestMapping("fileDel")
	public ResponseEntity<String> fileDel(String noticeNo, HttpSession session) {
		
		String rs = fs.delFile(noticeNo, session);
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
	}
	
	
	//첨부파일 다운로드
	@RequestMapping(value="fileDown", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> fileDownLoad(String fileNameSave, String filePath){
		
		Resource resource = new FileSystemResource(filePath+fileNameSave);
		
		if(!resource.exists()) {	
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
		String resourceName = resource.getFilename();
		resourceName = resourceName.substring(resourceName.indexOf("_")+1);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Disposition", 
					     "attachement; filename="+new String(resourceName.getBytes("utf-8"), "ISO-8859-1"));
		
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		
		
		
		
		
		
		
		
		
		
	}
	
	
}
