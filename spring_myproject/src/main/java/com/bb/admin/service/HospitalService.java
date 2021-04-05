package com.bb.admin.service;

import java.util.ArrayList;

import com.bb.admin.dto.Hospital;

public interface HospitalService {
	
	//병원 조회
	ArrayList<Hospital> getHospitalList();
	
	//병원 등록
	int insertHospital(Hospital h);
	
	//병원 수정
	int updateHospital(Hospital h);
	
	//병원 삭제
	int delHospital(String hospitalNo);
	
}
