package com.bb.admin.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.springframework.stereotype.*;

import com.bb.admin.dto.FileAttached;
import com.bb.admin.dto.Notice;
import com.bb.dbconn.DbConn;

import oracle.jdbc.OracleTypes;

@Repository
public class NoticeDao {

	private final Connection dbconn = DbConn.getInstanceOf().oracleConn();

	
	//공지사항 리스트 출력
	public ArrayList<Notice> getNoticeList() {
		
		ArrayList<Notice> noticeList = new ArrayList<>();
		
		try {
			String sql = "{call p_get_noticelist(?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(1);
			
			if(rs!= null) {
				while(rs.next()) {
					
					Notice n = new Notice();
					
					n.setnoticeNo(rs.getString("noticeno"));
					n.setWriter(rs.getString("email"));
					n.setTitle(rs.getString("title"));
					n.setContent(rs.getString("content"));
					n.setCount(rs.getString("count"));
					n.setWdate(rs.getString("wdate"));
					n.setMdate(rs.getString("mdate"));
					n.setGradeName(rs.getString("gradename"));
					
					noticeList.add(n);
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return noticeList;
	}


	//공지사항 등록
	public int insertNotice(Notice n) {
		
		int no = 0;
		
		FileAttached f = n.getFileAttached();
		
		try {
			String sql = "{call p_insert_notice(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, n.getWriter());
			stmt.setString(2, n.getTitle());
			stmt.setString(3, n.getContent());
			stmt.setString(4, f.getFileName());
			stmt.setString(5, f.getFileNameSave());
			stmt.setString(6, f.getFileSize());
			stmt.setString(7, f.getFileType());
			stmt.setString(8, f.getFilePath());
			stmt.registerOutParameter(9, OracleTypes.INTEGER);
			
			stmt.executeUpdate();
			
			no = stmt.getInt(9);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return no;
	}


	//공지사항 내용 보기
	public Notice getNoticeContent(String no) {
	
		Notice n = new Notice();
		
		try {
			String sql = "{call p_get_notice_content(?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, no);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			if(rs!=null) {
				
				if(rs.next()) {
					
					n.setContent(rs.getString("content"));
					n.setCount(rs.getString("count"));
					n.setGradeName(rs.getString("gradename"));
					n.setMdate(rs.getString("n_mdate"));
					n.setnoticeNo(rs.getString("noticeno"));
					n.setTitle(rs.getString("title"));
					n.setWdate(rs.getString("n_wdate"));
					n.setWriter(rs.getString("email"));
					
					if(rs.getString("noticefileno")!= null) {
						
						FileAttached f = new FileAttached();
						n.setFileAttached(f);
						
						f.setFileName(rs.getString("filename"));
						f.setFileNameSave(rs.getString("filenamesave"));
						f.setFileNo(rs.getString("noticefileno"));
						f.setFilePath(rs.getString("filepath"));
						f.setFileSize(rs.getString("filesize"));
						f.setFileType(rs.getString("filetype"));
						f.setNoticeNo(rs.getString("noticeno"));
						f.setWdate(rs.getString("f_wdate"));
						
					}
					
				}
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return n;
	}


	//공지사항 삭제
	public int deleteNotice(String noticeNo, HttpSession session) {
		
		int rs = 0;
		
		try {
			String sql = "{call p_del_notice(?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, noticeNo);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			
			rs = stmt.executeUpdate();
			
			ResultSet rsName = (ResultSet)stmt.getObject(2);
			
			
			
			if(rsName.next()) {
				
				ServletContext ctx = session.getServletContext();
				
				String path = ctx.getRealPath("resources/upload");
				
				File file = new File(path + File.separator + rsName.getString("filenamesave"));
				
			
				
				if(file.exists()) {
					file.delete();
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}

	//게시글 수정
	public String updateNotice(Notice n) {
		
		String rs = null;
		
		FileAttached f = n.getFileAttached();
		
		try {
			
			String sql = "{call  p_update_notice(?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			
			stmt.setString(1, n.getnoticeNo());
			stmt.setString(2, n.getTitle());
			stmt.setString(3, n.getContent());
			stmt.setString(4, f.getFileName());
			stmt.setString(5, f.getFileNameSave());
			stmt.setString(6, f.getFileSize());
			stmt.setString(7, f.getFileType());
			stmt.setString(8, f.getFilePath());
			
			stmt.executeUpdate();
			
			rs = n.getnoticeNo();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	
	
	
}
