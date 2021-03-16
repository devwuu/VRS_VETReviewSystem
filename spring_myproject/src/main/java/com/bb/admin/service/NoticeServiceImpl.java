package com.bb.admin.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.bb.admin.dao.NoticeDao;
import com.bb.admin.dto.FileAttached;
import com.bb.admin.dto.Notice;


@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDao nd;
	
	@Autowired
	private FileService fs;

	
	@Override
	public int noticeInsert(Notice n, HttpSession session, MultipartFile fileAttach) {
		
		n.setWriter((String)session.getAttribute("sess_id"));
		n = getFileInstance(n, session, fileAttach);
		
		return nd.insertNotice(n);
	}

	@Override
	public String noticeUpdate(Notice n, HttpSession session, MultipartFile fileAttach) {
		
		n = getFileInstance(n, session, fileAttach);
		
		return nd.updateNotice(n);
		
	}

	@Override
	public int noticeDelete(String noticeNo, HttpSession session) {
	
		return nd.deleteNotice(noticeNo, session);
	}

	@Override
	public ArrayList<Notice> getNoticeList() {
		
		return nd.getNoticeList();
	}
	

	@Override
	public Notice getNoticeContent(String no) {
		
		return nd.getNoticeContent(no);
	}
		
	
	//게시글 첨부파일 등록, 수정 메서드
	private Notice getFileInstance(Notice n, HttpSession session, MultipartFile fileAttach){
		
		FileAttached f = new FileAttached();
		n.setFileAttached(f);

		
		if(fileAttach != null) {
			if(fileAttach.getSize()>0) {				
				f = fs.instanceOf(f, session, fileAttach);
			}
		}

		return n;
	}


	

}
