package com.bb.admin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bb.admin.dto.Code;
import com.bb.admin.dto.Hospital;
import com.bb.dbconn.DbConn;

import oracle.jdbc.OracleTypes;

@Repository
public class HospitalDao {

	private Connection dbconn = DbConn.getInstanceOf().oracleConn();
	
	public ArrayList<Hospital> getHospitalList() {
		
		ArrayList<Hospital> hospitalList = new ArrayList<>();
		
		String sql = "{call p_get_hospital(?)}";
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			ResultSet rs = (ResultSet)stmt.getObject(1);
			
			String prevHospitalNo = "초기값";
			ArrayList<Code> tagList = new ArrayList<>();
			
			while(rs.next()) {
				
				
				if(!rs.getString("hospitalno").equals(prevHospitalNo)) {
					
					//병원 no가 달라졌을 때만 새로운 객체 생성
					//새로운 taglist 객체로 갱신
					tagList = new ArrayList<>();
					Hospital h = new Hospital();
					
					prevHospitalNo = rs.getString("hospitalno");
					
					h.setHospitalNo(rs.getString("hospitalno"));
					h.setHospitalName(rs.getString("hospitalname"));
					h.setHospitalTel(rs.getString("hospitaltel"));
					h.setHospitalAdd(rs.getString("hospitaladd"));
					h.setWdate(rs.getString("wdate"));
					h.setCode(tagList);
					
					hospitalList.add(h);
				}
				
				
				if(rs.getString("codename")!=null) {
					
					//병원 tag의 경우 next가 ture일 때 마다 새로 생성
					
					Code c = new Code();
					c.setCategory("병원태그");
					c.setCodeName(rs.getString("codename"));
					c.setCodeValue(rs.getString("codevalue"));
					
					tagList.add(c);
				}
				
			}
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return hospitalList;
	}

}
