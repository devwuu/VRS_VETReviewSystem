package com.bb.admin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.*;

import com.bb.admin.dto.Admin;
import com.bb.admin.dto.Member;
import com.bb.dbconn.DbConn;

import oracle.jdbc.OracleTypes;


@Repository
public class AdminDao {

	private final Connection dbconn = DbConn.getInstanceOf().oracleConn();
	
	public Admin loginAdmin(String id, String pw) {
		//관리자 로그인
		
		Admin admin = new Admin();
		
		try {
			String sql = "{call p_admin_login(?,?,?,?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, id);
			stmt.setString(2, pw);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.INTEGER);
			stmt.registerOutParameter(5, OracleTypes.INTEGER);
			
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(3);
			int regMem = stmt.getInt(4);
			int delMem = stmt.getInt(5);
			
			if(rs != null) {
				if(rs.next()) {
					
					admin.setEmail(rs.getString("email"));
					admin.setPw(rs.getString("pw"));
					admin.setGradeName(rs.getString("codename"));
					admin.setNickName(rs.getString("nickname"));
					admin.setRegMem(regMem);
					admin.setDelMem(delMem);
				}				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return admin;
	}

	//회원 전체 리스트 출력
	public ArrayList<Member> getMemberList() {
		
		ArrayList<Member> memberList = new ArrayList<>();
		
		try {
			String sql = "{call p_get_memlist(?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			ResultSet rs_memList = (ResultSet)stmt.getObject(1);
			ResultSet rs_memMag = (ResultSet)stmt.getObject(2);
			
			while(rs_memList.next()) {
				
				Member m = new Member();
				
				m.setEmail(rs_memList.getString("email"));
				m.setNickName(rs_memList.getString("nickname"));
				m.setWdate(rs_memList.getString("wdate"));
				m.setIsDel(rs_memList.getString("isdel"));
				m.setDelDate(rs_memList.getString("deldate"));
				m.setGradeName(rs_memList.getString("gradename"));

				
				memberList.add(m);

			}
			
			
			//member 객체에 추천 횟수/신고 횟수 저장
			memberList = countReport(rs_memMag, memberList);
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return memberList;
	}

	
	//탈퇴 요청 회원 출력
	public ArrayList<Member> getDelMemberList() {
		
		ArrayList<Member> delMemList = new ArrayList<>();
		
		try {
			String sql = "{call p_get_delmemlist(?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet memListCur = (ResultSet)stmt.getObject(1);
			ResultSet memMagCur = (ResultSet)stmt.getObject(2);
			
			if(memListCur != null) {
				while(memListCur.next()) {
					
					Member m = new Member();
					
					m.setEmail(memListCur.getString("email"));
					m.setNickName(memListCur.getString("nickname"));
					m.setWdate(memListCur.getString("wdate"));
					m.setIsDel(memListCur.getString("isdel"));
					m.setDelDate(memListCur.getString("deldate"));
					m.setGradeName(memListCur.getString("gradename"));
					
					delMemList.add(m);
					
				}
			}
			
			delMemList = countReport(memMagCur, delMemList);

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return delMemList;
	}

	
	//회원 검색 리스트 출력
	public ArrayList<Member> getSearchMemberList(String gradeCode, String condition) {
		
		ArrayList<Member> memberList = new ArrayList<>();
		
		try {
			String sql = "{call p_get_search_memlist(?, ?, ?,?)}";
			CallableStatement stmt= dbconn.prepareCall(sql);
			
			stmt.setString(1, gradeCode);
			stmt.setString(2, condition);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			
			ResultSet memCur = (ResultSet)stmt.getObject(3);
			ResultSet memMagCur = (ResultSet)stmt.getObject(4);
			
			if(memCur != null) {
				while(memCur.next()){
					
					Member m = new Member();
					
					m.setEmail(memCur.getString("email"));
					m.setNickName(memCur.getString("nickname"));
					m.setWdate(memCur.getString("wdate"));
					m.setIsDel(memCur.getString("isdel"));
					m.setDelDate(memCur.getString("deldate"));
					m.setGradeName(memCur.getString("gradename"));
					
					memberList.add(m);
					
				}
				
			}
			
			memberList = countReport(memMagCur, memberList);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return memberList;
	}

	
	//탈퇴 회원 검색 리스트 출력
	public ArrayList<Member> getSearchDelMemberList(String condition) {
		
		ArrayList<Member> memberList = new ArrayList<>();
		
		try {
			
			String sql = "{call  p_get_search_del_memlist(?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, condition);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet memCur = (ResultSet)stmt.getObject(2);
			ResultSet memMagCur = (ResultSet)stmt.getObject(3);
			
			if(memCur != null) {
				while(memCur.next()) {
					
					Member m = new Member();
					m.setEmail(memCur.getString("email"));
					m.setNickName(memCur.getString("nickname"));
					m.setWdate(memCur.getString("wdate"));
					m.setIsDel(memCur.getString("isdel"));
					m.setDelDate(memCur.getString("deldate"));
					m.setGradeName(memCur.getString("gradename"));
					
					memberList.add(m);
					
				}
			}
			
			memberList = countReport(memMagCur, memberList);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return memberList;
	}

	
	//회원탈퇴(회원정보삭제)진행
	public int delMember(String[] selectEmail) {
		
		int rs = 0;
		String sql = "UPDATE member SET pw = null, nickname = null,"
				     +" isdel = null WHERE email = ?";
		
		try {
			PreparedStatement stmt = dbconn.prepareStatement(sql);
			
			for(String email : selectEmail) {
				stmt.setString(1, email);
				rs = stmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	
	
	//회원의 추천/신고 건수 저장
	public ArrayList<Member> countReport(ResultSet rs, ArrayList<Member> memList) throws SQLException{
		
		while(rs.next()) {
			
			//email이 같은 member 객체에 추천/신고 건수 저장
			for(Member m : memList) {
				
				if(m.getEmail().equals(rs.getString("email"))) {
					
					m.setRecomCount(rs.getString("recommend"));
					m.setReportCount(rs.getString("report"));
				
				}
			}
			
		}
		
		
		return memList;
		
	}
	
	
	
	
	
	

}
