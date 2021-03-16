package com.bb.user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.*;

import com.bb.dbconn.DbConn;
import com.bb.user.dto.Code;
import com.bb.user.dto.Member;

import oracle.jdbc.OracleTypes;


@Repository
public class MemberDao {

	private final Connection conn = DbConn.getInstanceOf().oracleConn();
		
	
	//로그인
	public Map<String, String> memberLogin(String id, String pw) {
		
		Map<String, String> map = new HashMap<>();
		
		
		//1 로그인 성공
		//0 로그인 실패
		map.put("loginStat", "0");
		
		try { 
				String sql = "SELECT pw, nickname FROM member WHERE email = ? AND isdel = 'N'";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, id);
				ResultSet rs = stmt.executeQuery();
				
				if(rs.next()) {
					
					
					if(rs.getString("pw").equals(pw)) {					
						map.put("loginStat", "1");
						map.put("nickname",rs.getString("nickname"));
					}
				}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return map;
	}
	
	//회원가입
	public int memberJoin(Member m) {
		
		int stat=0;
		
		String sql = "{call P_JOIN_MEMBER(?, ?)}";
		
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, m.getEmail());
			stmt.setString(2, m.getPw());
			
			stat = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stat;
	}

	
	//회원 정보
	public Member memberInfor(String id) {
		
		Member m = new Member();
		Code c[] = new Code[10];
		int i = 0;
		
		String sql = "{call p_getInfor_member(?, ?)}";
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, id);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			rs.next();
			
			m.setEmail(id);
			m.setNickName(rs.getString("nickname"));
			m.setInterestCode(c);
			
			if(rs.getString("codevalue")!=null) {
				
				do {
					c[i] = new Code();
					c[i].setCategory("관심사");
					c[i].setCodeValue(rs.getString("codevalue"));
					c[i].setCodeName(rs.getString("codename"));
					i++;
				}while(rs.next());
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return m;
	}

	
	//회원정보수정
	public Map<String, String> memberUpdate(Member m) {
		
		Map<String, String> map = new HashMap<>();
		int stat = 0;

		try {
			
			String sql = "{call p_member_update(?,?,?,?)}";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, m.getPw());
			stmt.setString(2, m.getEmail());
			stmt.setString(3, m.getNickName());
			stmt.registerOutParameter(4, OracleTypes.INTEGER);
			stmt.executeUpdate();
			
			stat = stmt.getInt(4);
			//out 파라미터 : 0 == pw가 일치하지 않음
			//out 파라미터 : 1 == pw가 일치함			
			
			if(stat >0) {
				map.put("pwCheck", "ok");
				//pw가 일치하면
				
				Code[] interest = m.getInterestCode();
				
				if(interest != null) {
					sql = "{call p_delete_interest(?)}";
					stmt = conn.prepareCall(sql);
					stmt.setString(1, m.getEmail());
					stmt.executeUpdate();
					
					for(Code c : interest) {
						if(c != null) {
							sql = "{call p_insert_interest(?,?)}";
							stmt = conn.prepareCall(sql);
							stmt.setString(1, c.getCodeValue());
							stmt.setString(2, m.getEmail());
							stmt.executeUpdate();							
						}
						
					}
				}
			
			}else {
				//pw가 일치하지 않으면
				map.put("pwCheck", "notOk");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	//이메일 중복검사
	public int emailCheck(String emailRequest) {
		
		int cnt = 0;
		
		try {
			String sql ="SELECT COUNT(*) cnt FROM member WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, emailRequest);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			cnt = rs.getInt("cnt");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return cnt;
	}

	
	//회원 탈퇴 요청
	public int memberDel(String email, String pw) {
		
		int rs = 0;
		
		try {
			String sql = "{call  p_del_member(?, ?,?)}";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, email);
			stmt.setString(2, pw);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);

			stmt.executeUpdate();
			
			rs = stmt.getInt(3);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	
	
	
	

}
