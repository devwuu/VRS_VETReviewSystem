package com.bb.admin.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.bb.admin.dao.HospitalDao;
import com.bb.admin.dto.Hospital;

@Service
public class HospitalServiceImpl implements HospitalService {

	private HospitalDao hd = new HospitalDao();
	
	@Override
	public ArrayList<Hospital> getHospitalList() {
		return hd.getHospitalList();
	}

	@Override
	public int insertHospital(Hospital h) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateHospital(Hospital h) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delHospital(String hospitalNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
