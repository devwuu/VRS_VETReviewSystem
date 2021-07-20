package com.bb.user.dto;

import java.util.ArrayList;

public class Hospital {

	private String hospitalNo;
	private String hospitalName;
	private String hospitalTel;
	private String post;
	private String hospitalAdd1;
	private String hospitalAdd2;
	private String hospitalAdd3;
	private String wdate;
	
	//hos list 출력용 list
	private ArrayList<Code> code;
	
	//reg form, mod form용 tag
	private String[] hostag;
	

	
	
	public String[] getHostag() {
		return hostag;
	}
	public void setHostag(String[] hostag) {
		this.hostag = hostag;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getHospitalAdd1() {
		return hospitalAdd1;
	}
	public void setHospitalAdd1(String hospitalAdd1) {
		this.hospitalAdd1 = hospitalAdd1;
	}
	public String getHospitalAdd2() {
		return hospitalAdd2;
	}
	public void setHospitalAdd2(String hospitalAdd2) {
		this.hospitalAdd2 = hospitalAdd2;
	}
	public String getHospitalAdd3() {
		return hospitalAdd3;
	}
	public void setHospitalAdd3(String hospitalAdd3) {
		this.hospitalAdd3 = hospitalAdd3;
	}
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


	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	
	
	
	
	
	
	
}
