package com.bb.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.bb.admin.dao.HospitalDao;
import com.bb.admin.dto.Hospital;

@Service
public class HospitalServiceImpl implements HospitalService {

	private HospitalDao hd = new HospitalDao();
	
	@Override
	public HashMap<String, Object> getHospitalList() {
		return hd.getHospitalList();
	}

	@Override
	public int insertHospital(Hospital h) {
		
		return hd.regHospital(h);
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
