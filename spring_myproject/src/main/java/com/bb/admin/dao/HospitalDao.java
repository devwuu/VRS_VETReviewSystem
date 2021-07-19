package com.bb.admin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.bb.admin.dto.Code;
import com.bb.admin.dto.Hospital;
import com.bb.dbconn.DbConn;

import oracle.jdbc.OracleTypes;

@Repository
public class HospitalDao {

	private Connection dbconn = DbConn.getInstanceOf().oracleConn();
	
	
	//병원 리스트 가져오기
	public HashMap<String, Object> getHospitalList() {
		
		//return
		HashMap<String, Object> result = new HashMap<>();
		
		//병원 리스트 저장
		ArrayList<Hospital> hospitalList = new ArrayList<>();
		
		//code 전체 리스트 저장
		ArrayList<Code> codeList = new ArrayList<>();
		
		String sql = "{call p_get_hospital(?,?)}";
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(1);
			ResultSet rsCode = (ResultSet)stmt.getObject(2);
			
			String prevHospitalNo = "초기값";
			ArrayList<Code> tagList = new ArrayList<>();
			
			while(rs.next()) {
				
				
				if(!rs.getString("hospitalno").equals(prevHospitalNo)) {
					
					//병원 no가 달라졌을 때만 새로운 객체 생성
					//새로운 taglist 객체로 갱신(기존의 hospital tag 초기화)
					tagList = new ArrayList<>();
					Hospital h = new Hospital();
					
					prevHospitalNo = rs.getString("hospitalno");
					
					h.setHospitalNo(rs.getString("hospitalno"));
					h.setHospitalName(rs.getString("hospitalname"));
					h.setHospitalTel(rs.getString("hospitaltel"));
					h.setPost(rs.getString("post"));
					h.setHospitalAdd1(rs.getString("hospitaladd1"));
					h.setHospitalAdd2(rs.getString("hospitaladd2"));
					h.setHospitalAdd3(rs.getString("hospitaladd3"));
					h.setWdate(rs.getString("wdate"));
					h.setCode(tagList);
					
					hospitalList.add(h);
				}
				
				
				if(rs.getString("codename")!=null) {
					
					//병원 tag의 경우, null이 아니고 next가 ture일 때 마다 새로 생성
					
					Code c = new Code();
					c.setCategory("병원태그");
					c.setCodeName(rs.getString("codename"));
					c.setCodeValue(rs.getString("codevalue"));
					
					tagList.add(c);
				}
				
			}
			
			result.put("HospitalList", hospitalList);
			
			//전체 codeList 저장
			while(rsCode.next()) {
				
				Code c = new Code();
				c.setCodeNo(rsCode.getString("codeno"));
				c.setCategory(rsCode.getString("category"));
				c.setCodeName(rsCode.getString("codename"));
				c.setCodeValue(rsCode.getString("codevalue"));
				
				codeList.add(c);
			}
			
			result.put("codeList", codeList);
			
			rs.close();
			rsCode.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	
	//신규 병원 등록
	public int regHospital(Hospital h) {
		
		int rs = 0;
		
		try {
			String sql = "{call p_insert_hospital(?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, h.getHospitalName());
			stmt.setString(2, h.getHospitalTel());
			stmt.setString(3, h.getPost());
			stmt.setString(4, h.getHospitalAdd1());
			stmt.setString(5, h.getHospitalAdd2());
			stmt.setString(6, h.getHospitalAdd3());
			stmt.registerOutParameter(7, OracleTypes.INTEGER);
			
			rs = stmt.executeUpdate();
			
			int hosNo = stmt.getInt(7);
			
			
			if(h.getHostag() != null) {
				
				sql = "INSERT INTO hospitaltag (tagno, hospitalno, codevalue) "
						+ "VALUES (TAGNO.nextval, ?, ?)";
				
				PreparedStatement stmtTag = dbconn.prepareStatement(sql);
				
				for(String codeValue : h.getHostag()) {
					if(codeValue != null) {
						stmtTag.setInt(1, hosNo);
						stmtTag.setString(2, codeValue);
						rs = stmtTag.executeUpdate();
					}
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}



	//병원 수정
	public int updateHospital(Hospital h) {
		
		int rs = 0;
		
		try {
			String sql = "{call p_update_hospital(?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, h.getHospitalNo());
			stmt.setString(2, h.getHospitalName());
			stmt.setString(3, h.getHospitalTel());
			stmt.setString(4, h.getPost());
			stmt.setString(5, h.getHospitalAdd1());
			stmt.setString(6, h.getHospitalAdd2());
			stmt.setString(7, h.getHospitalAdd3());
			
			rs = stmt.executeUpdate();
			
			if(h.getHostag() != null) {
				
				sql = "DELETE FROM hospitaltag WHERE hospitalno = ?";
				PreparedStatement stmtP = dbconn.prepareStatement(sql);
				stmtP.setString(1, h.getHospitalNo());
				
				stmtP.executeUpdate();
				
				sql = "INSERT INTO hospitaltag (tagno, hospitalno, codevalue)"
					  +"VALUES (tagno.nextval, ?,?)";
				
				stmtP = dbconn.prepareStatement(sql);
				stmtP.setString(1, h.getHospitalNo());					
				
				for(String s : h.getHostag()) {
					stmtP.setString(2, s);
					rs = stmtP.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}


	//병원 삭제
	public int deleteHospital(String[] hospitalNo) {
		
		int rs = 0;
		
		try {
			String sql = "{call p_del_hospital(?)}";
			
			for(String hosNo:hospitalNo) {
				
				CallableStatement stmt = dbconn.prepareCall(sql);
				stmt.setString(1, hosNo);
				rs = stmt.executeUpdate();
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return rs;
	}

}
