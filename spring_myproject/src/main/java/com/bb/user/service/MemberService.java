package com.bb.user.service;

import java.util.Map;

import com.bb.user.dto.Member;



public interface MemberService {
	
	//로그인
	Map<String, String> login(String id, String pw);
	
	//회원가입
	int join(Member m);
	
	//회원 정보 출력(1인)
	Member getInfor(String id);

	//회원 정보 수정
	Map<String, String> update(Member m);

	//이메일 중복검사
	int emailCheck(String emailRequest);

	//회원 탈퇴 요청
	int delMember(String email, String pw);
	
	//회원 정보 찾기
	int findMember(String email);
	
}
