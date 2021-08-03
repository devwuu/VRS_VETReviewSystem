package com.bb.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.bb.admin.dto.Code;
import com.bb.dbconn.DbConn;

@Repository
public class TagDao {

	private final Connection dbConn = DbConn.getInstanceOf().oracleConn();
	
	//태그 리스트 출력
	public ArrayList<Code> getTagList() {
		
		ArrayList<Code> codeList = new ArrayList<>();
		
		String sql = "SELECT codeno,"
				+ "       category,"
				+ "       codename,"
				+ "       codevalue,"
				+ "       to_char(wdate,'YY-MM-DD') wdate "
				+ "FROM code "
				+ "WHERE category='병원태그' "
				+ "ORDER BY codevalue DESC";
		
		try {
			PreparedStatement stmt = dbConn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Code c  = new Code();
				
				c.setCategory("병원태그");
				c.setCodeName(rs.getString("codename"));
				c.setCodeNo(rs.getString("codeno"));
				c.setCodeValue(rs.getString("codevalue"));
				c.setWdate(rs.getString("wdate"));
				
				codeList.add(c);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return codeList;
	}

	
	//tag value 중복 검사
	public int checkTagValue(String checkValue) {
		
		int result = 0;
		
		try {
			String sql = "SELECT count(*) as cnt FROM code "
					+ "WHERE category = '병원태그' AND codevalue = ? ";
			
			PreparedStatement stmt = dbConn.prepareStatement(sql);
			
			stmt.setString(1, checkValue);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}


	//태그 등록
	public int insertTag(Code code) {
		
		int rs = 0;
		
		try {
			String sql = "INSERT INTO code(codeno, category, codename, codevalue)"
					+ " VALUES (codeno.nextval, '병원태그', ?, ?)";
		
			PreparedStatement stmt = dbConn.prepareStatement(sql);

			stmt.setString(1, code.getCodeName());
			stmt.setString(2, code.getCodeValue());
		
			rs = stmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}


	
	//코드 삭제
	public int deleteTage(String[] codeValue) {

		String sql = "DELETE FROM code WHERE category='병원태그' AND codevalue = ?";
		int rs = 0;
		
		try {
			
			for(String cv : codeValue) {
				PreparedStatement stmt = dbConn.prepareCall(sql);
				stmt.setString(1, cv);
				
				rs = stmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return rs;
	}

}
