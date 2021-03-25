package com.bb.user.dao;

import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.stereotype.*;

import com.bb.dbconn.DbConn;

import oracle.jdbc.*;



@Repository
public class FileDao {
	
	
	private final Connection dbconn = DbConn.getInstanceOf().oracleConn();
	
	//추후에 파일 insert 기능을 review 프로시져와 분리하도록 함
	//첨부파일이 많아질경우, 프로시져가 분리되어 있어야 처리하기 수월하기 때문에
	// 첨부파일 갯수를 늘리고 프로시져를 분리하도록 함

	//첨부파일 삭제(review 수정 form에서 넘어옴 ajax)
		public int delFile(String reviewNo, HttpSession session) {
			
			int rs = 0;
			try {
				String sql = "{call p_delfile(?, ?)}";
				CallableStatement stmt = dbconn.prepareCall(sql);
				stmt.setString(1, reviewNo);
				stmt.registerOutParameter(2, OracleTypes.CURSOR);
				
				stmt.executeQuery();
				
				ResultSet rsSet = (ResultSet)stmt.getObject(2);
				
				if(rsSet.next()) {
					ServletContext ctx = session.getServletContext();
					String delFilepath = ctx.getRealPath("resources/upload");
					File file = new File(delFilepath, rsSet.getString("filenamesave"));
					
					
					if(file.exists()) {
						file.delete();
						rs = 1;
					}
				
				}
				
				rsSet.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			return rs;
		}

		
		
		
		
	//sns게시글 첨부파일 삭제	
		public String delSnsFile(String snsReviewNo, HttpSession session) {
			
			String sql = "{call p_del_snsfileup(?, ?)}";
			String stat = null;
			
			try {
				CallableStatement stmt = dbconn.prepareCall(sql);
				stmt.setString(1, snsReviewNo);
				stmt.registerOutParameter(2, OracleTypes.CURSOR);
				stmt.executeUpdate();
				
				ResultSet rs = (ResultSet)stmt.getObject(2);
				if(rs != null && rs.next()) {
					
					//일반 게시판 첨부파일 삭제 메서드와 중복되는 부분.
					ServletContext ctx = session.getServletContext();
					String filePath = ctx.getRealPath("resources/upload");
					File file = new File(filePath, rs.getString("filenamesave"));
					
					if(file.exists()) {
						file.delete();
						stat = "1";
					}
					
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return stat;
		}
	
	
}
