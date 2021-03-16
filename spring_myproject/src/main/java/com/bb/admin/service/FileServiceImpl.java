package com.bb.admin.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.bb.admin.dao.FileDao;
import com.bb.admin.dto.FileAttached;


@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileDao fd;
	
	@Override
	public String delFile(String noticeNo, HttpSession session) {
	
		return fd.delFile(noticeNo, session);
	}
	
	

	@Override
	public FileAttached instanceOf(FileAttached f, HttpSession session, MultipartFile fileAttach) {
		//파일 객체 정보 셋팅
		
		ServletContext stx = session.getServletContext();
		String filePath =  stx.getRealPath("resources/upload")+File.separator;
		
		String fileName = fileAttach.getOriginalFilename();
		f.setFileName(fileName);
		
		f.setFileSize(Long.toString(fileAttach.getSize()));
		f.setFilePath(filePath);
		f.setFileType(fileAttach.getContentType());
		
		UUID uid = UUID.randomUUID();
		String saveFileName = uid +"_"+ fileName;
		
		f.setFileNameSave(saveFileName);
		
		File file = new File(filePath+saveFileName);
		
		System.out.println(filePath+saveFileName);
		
		try {
			
			fileAttach.transferTo(file);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return f;
	}
	
}
