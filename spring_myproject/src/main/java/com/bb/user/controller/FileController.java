package com.bb.user.controller;


import java.io.*;

import javax.servlet.http.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.bb.user.service.FileService;



@Controller
@RequestMapping("/file/")
public class FileController{
	
	private final Logger log = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileService fs;
	
	
	//첨부 파일 삭제
    @RequestMapping("fileDel") 
    public ResponseEntity<String> fileDel(@RequestParam("seqno_r") String reviewNo,
    								  	  HttpSession session){
    	
		String rs = Integer.toString(fs.delFile(reviewNo, session));
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
    }
    

	//sns형 첨부파일 삭제
	@RequestMapping("snsFileDel")
	public ResponseEntity<String> snsFileDel(String snsReviewNo, HttpSession session){
		
		String rs = fs.delSnsFile(snsReviewNo, session);
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
	}
	
    
    
    //첨부파일 다운로드
    //service로 옮길까?
    @RequestMapping(value="download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> fileDownLoad(String fileNameSave, String filePath) {
    	
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
