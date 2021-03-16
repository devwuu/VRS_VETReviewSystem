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
    
    
    //첨부파일 다운로드
    //미디어 파일이기 때문에 produces를 셋팅해준다. 따라서 value도 따로 셋팅해주도록 한다.
    //responsebody어노테이션도 넣어줘야 합니다. 파일 응답으로 할거라
    //service로 옮길까?
    @RequestMapping(value="download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> fileDownLoad(String fileNameSave, String filePath) {
    	//파일 다운로드시 제네릭 타입은 리소스로 해주면 된다. 스프링 프레임 워크가 가지고 있는 타입
    	
    	Resource resource = new FileSystemResource(filePath+fileNameSave);
    	//파일경로를(파일이름 포함한 경로이다.) 생성자에 매개변수로 던져주면 됩니다.

    	if(!resource.exists()) {
    		//만약 이 리소스(미디어 파일)이 존재하지 않으면

    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		//해당 경로에 파일이 존재하지 않으면 에러를 return 합니다.
    	}

    	
    	String resourceName = resource.getFilename();
    	resourceName = resourceName.substring(resourceName.indexOf("_")+1);
    	//uuid 제거를 위해 subString 해줍니다.
    	
    	HttpHeaders headers = new HttpHeaders();
    	//http 프로토콜의 경우 헤더를 셋팅해 줘야 합니다.
    	
    	try {
			headers.add("Content-Disposition",
						"attachement; filename="+new String(resourceName.getBytes("utf-8"), "ISO-8859-1"));
			
			//헤더에 담을 키값은 브라우저마다 정해져있다(매뉴얼임). 따라서 브라우저마다 다르게 설정해주면 됨
			//이건 구글 기준임
			//resourece name을 파일이름으로 하여 다운로드 하게 됩니다.
    	
    	} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
    	
    	return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    

    
    
    
    
    
}
