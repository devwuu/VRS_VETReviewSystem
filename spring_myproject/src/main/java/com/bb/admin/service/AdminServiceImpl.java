package com.bb.admin.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.bb.admin.dao.AdminDao;
import com.bb.admin.dto.Admin;
import com.bb.admin.dto.Member;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao ad;
	
	@Override
	public Admin login(String id, String pw) {
		return ad.loginAdmin(id, pw);
	}

	@Override
	public ArrayList<Member> getMemberList() {
		return ad.getMemberList();
	}

	@Override
	public ArrayList<Member> getDelMemberList() {
		return ad.getDelMemberList();
	}

	@Override
	public ArrayList<Member> getSearchMember(String gradeCode, String condition) {
		
		return ad.getSearchMemberList(gradeCode, condition);
	}

	@Override
	public ArrayList<Member> getSearchDelMember(String condition) {
		return ad.getSearchDelMemberList(condition);
	}

	@Override
	public int delMember(String[] selectEmail) {
		return ad.delMember(selectEmail);
	}

}
