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
			
			String sql = "{call p_member_update(?,?,?,?,?)}";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, m.getPw());
			stmt.setString(2, m.getPwUpdate());
			stmt.setString(3, m.getEmail());
			stmt.setString(4, m.getNickName());
			stmt.registerOutParameter(5, OracleTypes.INTEGER);
			stmt.executeUpdate();
			
			stat = stmt.getInt(5);
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

	
	
	//pw찾기용 이메일 유효성 체크
	public int emailCheckPw(String emailRequest) {
		int cnt = 0;
		
		try {
			String sql ="SELECT COUNT(*) cnt FROM member WHERE email = ? AND deldate IS NULL";
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
	
	
	
	
	//임시 비밀번호 발급
	public int updatePw(String email, String tmpPw) {
		 
		int rs = 0;
		
		try {
			String sql = "UPDATE member SET pw = ? WHERE email = ? AND deldate IS NULL";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tmpPw);
			stmt.setString(2, email);
			rs = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return rs;
	}

	
	//회원 추천
	public int recommendUser(String sessionId, String recomUser) {
		
		String sql = "INSERT INTO membermag(memmagno, from_mem, to_mem, magcode) "
				   + "VALUES (MEMMAGNO.nextval, ?, ?, 1)";		
		
		int rs = 0;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, sessionId);
			stmt.setString(2, recomUser);
			
			rs = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}

	
	//회원 신고
	public int reportUser(String sessionId, String reportUser) {
		
		String sql = "INSERT INTO membermag(memmagno, from_mem, to_mem, magcode) "
				   + "VALUES (MEMMAGNO.nextval, ?, ?, 2)";		
		
		int rs = 0;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, sessionId);
			stmt.setString(2, reportUser);
			
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}

	
	
	//인증 코드 생성(회원 가입, 비밀번호 변경용)
	public int randomCode(String emailRequest, String code) {
		
		String sql  = "{call  p_certification_check(?,?)}";
		int rs = 0;
		
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, emailRequest);
			stmt.setString(2, code);
			
			rs = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	
	//인증 번호 확인
	public int cerCodeCheck(String cerCode, String email) {
		
		String sql = "SELECT code FROM certification WHERE email = ?";
		int result = 0;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			if(cerCode.equals(rs.getString("code"))) {
				result = 1;
			}				
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return result;
	}

	

	
	
	
	
	

}
