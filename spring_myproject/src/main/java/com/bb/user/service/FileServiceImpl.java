package com.bb.user.service;

import java.io.*;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.bb.user.dao.FileDao;
import com.bb.user.dto.*;



@Service
public class FileServiceImpl implements FileService {
	
	private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Autowired
	private FileDao fd;

	
	
	@Override
	public FileAttached getInstanceOf(FileAttached f, MultipartFile fileAttach, HttpSession session) {
		//파일 정보를 담은 객체를 return
		
		ServletContext ctx = session.getServletContext();
		
		String filePath = ctx.getRealPath("resources/upload")+File.separator;
		// 루트 밑의 upload폴더의 실제 경로를 가져옴
		
		String fileName = fileAttach.getOriginalFilename();
		
		f.setFileName(fileName);
		
		f.setFileSize(Long.toString(fileAttach.getSize()));
		f.setFileType(fileAttach.getContentType());
		f.setFilePath(filePath);
		
		UUID uid = UUID.randomUUID();
		
		String saveFileName = uid.toString()+"_"+fileName;
		//저장할 때의 이름을 정해준다. 파일이름: uuid_사용자가정한파일이름
		
		f.setFileNameSave(saveFileName);
		
		File file = new File(filePath, saveFileName);
		//파일을 write 할 때는 디렉토리와 파일 이름까지 정해줘야 하기 때문에 
		//파일이 저장될 디렉토리 경로와 저장할 파일 이름을 매개변수로 던져준다(이렇게 하면 파일 구분자가 없어도 됨)
		//으로 파일을 생성하여서 매개변수로 write 해준디.
		
		log.info("file upload: "+filePath +saveFileName);
		//실제로 파일이 저장되는 경로
		
		try {
			
			fileAttach.transferTo(file);
			//파일 저장
			
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return f;
	}
	
	
	

	@Override
	public int delFile(String reviewNo, HttpSession session) {
		//첨부파일 삭제
		return fd.delFile(reviewNo, session);
	}




	@Override
	public String delSnsFile(String snsReviewNo, HttpSession session) {
		// sns게시글 삭제
		return fd.delSnsFile(snsReviewNo, session);
	}








}
