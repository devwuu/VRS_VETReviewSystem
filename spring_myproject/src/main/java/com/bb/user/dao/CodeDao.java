package com.bb.user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bb.dbconn.DbConn;
import com.bb.user.dto.Code;

import oracle.jdbc.OracleTypes;

public class CodeDao {

	private Connection conn = DbConn.getInstanceOf().oracleConn();
	
	public ArrayList<Code> codeList(String category) {
		
		ArrayList<Code> code = new ArrayList<>();
 		
		String sql = "{call p_get_code(?, ?)}";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, category);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			
			while(rs.next()) {
				Code c = new Code();
				c.setCategory(category);
				c.setCodeName(rs.getString("codename"));
				c.setCodeValue(rs.getString("codevalue"));
				
				code.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return code;
	}

}
