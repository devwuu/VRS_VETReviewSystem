package com.bb.user.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.bb.user.dao.MemberDao;
import com.bb.user.dto.Member;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao mDao;
	
	
	@Override
	public Map<String, String> login(String id, String pw) {

		return mDao.memberLogin(id, pw);
	}

	@Override
	public int join(Member m) {
		return mDao.memberJoin(m);
	}

	@Override
	public Member getInfor(String id) {
		return  mDao.memberInfor(id);
	}

	@Override
	public Map<String, String> update(Member m) {
		return mDao.memberUpdate(m);
	}
	

	@Override
	public int emailCheck(String emailRequest) {
		
		int emailCnt = mDao.emailCheck(emailRequest);
		String code = "";
		
		//중복된 이메일이 없을 시 인증 코드 생성 및 이메일 발송
		if(emailCnt < 1) {
			try {
				
				code = randomCode(emailRequest, 2);
				
				//DB 반영
				mDao.randomCode(emailRequest, code);
				
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (EmailException e) {

				e.printStackTrace();
			}
		}
		
		
		return emailCnt;
	}

	  
	@Override
	public int delMember(String email, String pw) {
		return mDao.memberDel(email, pw);
	}

	
	
	@Override
	public int findMember(String email) {
		
		String tmpPw = "";
		
		try {
			
			//임시 비밀번호 생성
			tmpPw = randomCode(email, 1);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
		
		//DB 반영
		return mDao.updatePw(email, tmpPw);
		
	}
	

	@Override
	public int recommendUser(String sessionId, String recomUser) {
		
		return mDao.recommendUser(sessionId, recomUser);
	}

	@Override
	public int reportUser(String sessionId, String reportUser) {
		
		return mDao.reportUser(sessionId, reportUser);
	}
	
	@Override
	public int cerCodeCheck(String cerCode, String email) {
		return mDao.cerCodeCheck(cerCode, email);
	}
	
	@Override
	public int emailCheckPw(String emailRequest) {
		
		String code= "";
		int rs = 0;
		
		//등록된 이메일이 있을 경우에
		if(mDao.emailCheckPw(emailRequest)>0) {
			
			try {
				
				//인증 코드 메일 발송
				code = randomCode(emailRequest, 2);	
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (EmailException e) {
				
				e.printStackTrace();
			}
			
			//DB에 인증 코드 저장
			rs =  mDao.randomCode(emailRequest, code);
		}
		
		
		return rs;
	}

	
	
	
	
	
	
	
	
	
	
	//난수 생성 및 이메일 발송
	// 코드 타입:
	// 1. 임시 비밀번호 생성 및 이메일 발송
	// 2. 인증번호 생성 및 이메일 발송
	public String randomCode(String email, int codeType) throws FileNotFoundException, IOException, EmailException {
		
		
		
		Properties p = new Properties();
		String path = MemberServiceImpl.class.getResource("emailConn.properties").getPath();
		String charSet = "utf-8";		
		String tmpCode = UUID.randomUUID().toString();
		tmpCode = tmpCode.substring(0, 6);
		
		
		path = URLDecoder.decode(path, charSet);
		p.load(new FileReader(path));
					
		String hostSMTP = "smtp.naver.com";
		String hostEmail = p.getProperty("hostEmail");
		String hostPw = p.getProperty("hostPw");
		
		String title = "";
		String meg = "";
		
		
		switch(codeType) {
		
			case 1:
				title = "[동물 병원 후기 모음집] 임시 비밀번호 발급";
				meg = "<br><br><br><br>"+email+"님의 임시 비밀번호: <b>"+tmpCode+"</b>";
				meg += "<br> 로그인 후 반드시 비밀번호를 변경하여주세요.";
				meg += "<br><br> 문의 : admin@naver.com";
				
				
				break;
				
				
			case 2:
				
				
				title = "[동물 병원 후기 모음집] 인증코드 발급";
				meg = "<br><br><br><br>"+email+"님의 인증번호: <b>"+tmpCode+"</b>";
				meg += "<br> 인증번호를 정확히 입력해주세요";
				meg += "<br><br> 문의 : admin@naver.com";
				
				
				break;
		}
		
		HtmlEmail htmlEmail = new HtmlEmail();
//		htmlEmail.setDebug(true);
		htmlEmail.setCharset(charSet);
		htmlEmail.setSSL(true);
		htmlEmail.setHostName(hostSMTP);
		htmlEmail.setSmtpPort(465);
		htmlEmail.setAuthentication(hostEmail, hostPw);
		htmlEmail.setTLS(true);
		htmlEmail.addTo(email, email,charSet);
		htmlEmail.setFrom(hostEmail, "동물병원 아카이빙 시스템", charSet);
		htmlEmail.setSubject(title);
		htmlEmail.setHtmlMsg(meg);
		htmlEmail.send();
		
		return tmpCode;
	}


}
