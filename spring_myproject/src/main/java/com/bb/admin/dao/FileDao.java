package com.bb.admin.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.springframework.stereotype.*;

import com.bb.dbconn.DbConn;

import oracle.jdbc.OracleTypes;


@Repository
public class FileDao {

	private final Connection dbconn = DbConn.getInstanceOf().oracleConn();
	
	
	//공지사항 첨부 파일 삭제(ajax)
		public String delFile(String noticeNo, HttpSession session) {
			
			String stat = "0";
			
			try {
				String sql = "{call p_delfile_notice(?, ?)}";
				CallableStatement stmt = dbconn.prepareCall(sql);
				stmt.setString(1, noticeNo);
				stmt.registerOutParameter(2, OracleTypes.CURSOR);
				
				stmt.executeUpdate();
				
				ResultSet rs = (ResultSet)stmt.getObject(2);
				
				stat = "1";
				
				if(rs != null) {
					if(rs.next()) {
						
						String fileSaveName = rs.getString("filenamesave");
						
						ServletContext cxt = session.getServletContext();
						String path = cxt.getRealPath("resources/upload");
						
						File file = new File(path+File.separator+fileSaveName);
						
						if(file.exists()) {
							file.delete();
						}
						
					}
					
				}
			
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
			return stat;
		}

	
}
