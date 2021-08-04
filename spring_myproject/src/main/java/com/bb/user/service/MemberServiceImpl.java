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
		return mDao.emailCheck(emailRequest);
	}

	@Override
	public int delMember(String email, String pw) {
		return mDao.memberDel(email, pw);
	}

	@Override
	public int findMember(String email) {
		
		//임시 비밀번호 이메일 발송
		
		Properties p = new Properties();
		String path = MemberServiceImpl.class.getResource("emailConn.properties").getPath();
		String charSet = "utf-8";		
		String tmpPw = UUID.randomUUID().toString();
		tmpPw = tmpPw.substring(0, 6);
		
		try {

			
			path = URLDecoder.decode(path, charSet);
			p.load(new FileReader(path));
						
			String hostSMTP = "smtp.naver.com";
			String hostEmail = p.getProperty("hostEmail");
			String hostPw = p.getProperty("hostPw");
			

			String title = "[동물병원 아카이빙 시스템] 임시 비밀번호 발급";
			String meg = "<br><br><br><br>"+email+"님의 임시 비밀번호: <b>"+tmpPw+"</b>";
			meg += "<br> 로그인 후 반드시 비밀번호를 변경하여주세요.";
			
			HtmlEmail htmlEmail = new HtmlEmail();
//			htmlEmail.setDebug(true);
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
			
			
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
			
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

}
