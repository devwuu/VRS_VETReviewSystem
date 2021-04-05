package com.bb.admin.dto;

import java.util.ArrayList;

public class Hospital {

	private String hospitalNo;
	private String hospitalName;
	private String hospitalTel;
	private String hospitalAdd;
	private String wdate;
	private ArrayList<Code> code;
	
	
	//Code와 tag중 어느쪽을 사용할지.....
	//미사용 20200405ver
	private ArrayList<HospitalTag> hospitalTag;
	
	
	public ArrayList<Code> getCode() {
		return code;
	}
	public void setCode(ArrayList<Code> code) {
		this.code = code;
	}
	public String getHospitalNo() {
		return hospitalNo;
	}
	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getHospitalTel() {
		return hospitalTel;
	}
	public void setHospitalTel(String hospitalTel) {
		this.hospitalTel = hospitalTel;
	}
	public String getHospitalAdd() {
		return hospitalAdd;
	}
	public void setHospitalAdd(String hospitalAdd) {
		this.hospitalAdd = hospitalAdd;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public ArrayList<HospitalTag> getHospitalTag() {
		return hospitalTag;
	}
	public void setHospitalTag(ArrayList<HospitalTag> hospitalTag) {
		this.hospitalTag = hospitalTag;
	}
	
	
	
	
	
	
	
}
