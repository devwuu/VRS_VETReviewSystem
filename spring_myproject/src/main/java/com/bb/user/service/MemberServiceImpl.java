package com.bb.user.service;

import java.util.Map;

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

}
