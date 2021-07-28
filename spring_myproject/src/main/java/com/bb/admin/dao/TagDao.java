package com.bb.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

}
