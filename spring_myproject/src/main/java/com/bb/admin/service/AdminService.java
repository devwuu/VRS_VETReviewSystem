package com.bb.admin.service;

import java.util.ArrayList;

import com.bb.admin.dto.Admin;
import com.bb.admin.dto.Member;

public interface AdminService {
	
	//로그인
	Admin login(String id, String pw);
	
	//회원 리스트 출력
	ArrayList<Member> getMemberList();

	//탈퇴 요청 회원 리스트 출력
	ArrayList<Member> getDelMemberList();

	//검색 회원 리스트 출력
	ArrayList<Member> getSearchMember(String gradeCode, String condition);

	//탈퇴 회원 검색 리스트 출력
	ArrayList<Member> getSearchDelMember(String condition);

}
