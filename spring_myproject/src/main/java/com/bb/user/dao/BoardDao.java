package com.bb.user.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.springframework.stereotype.*;

import com.bb.dbconn.DbConn;
import com.bb.user.dto.Code;
import com.bb.user.dto.FileAttached;
import com.bb.user.dto.Hospital;
import com.bb.user.dto.Notice;
import com.bb.user.dto.Page;
import com.bb.user.dto.Reply;
import com.bb.user.dto.Review;
import com.bb.user.dto.SnsReview;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;



@Repository
public class BoardDao {
	
	private final Connection dbconn = DbConn.getInstanceOf().oracleConn();

	
	
	//리뷰 등록
	public String insertBoard(Review r) {
		
		String reviewNo = null;
		FileAttached f = r.getFileAttached();
		
		String sql = "{call p_insert_review(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, r.getHospitalNo());
			stmt.setString(2, r.getWriter());
			stmt.setString(3, r.getTitle());
			stmt.setString(4, r.getContent());
			stmt.setString(5, r.getReviewScore());
			stmt.setString(6, f.getFileName());
			stmt.setString(7, f.getFileNameSave());
			stmt.setString(8, f.getFileSize());
			stmt.setString(9, f.getFileType());
			stmt.setString(10, f.getFilePath());
			stmt.registerOutParameter(11, OracleTypes.INTEGER);
			
			stmt.executeUpdate();
			
			reviewNo = stmt.getString(11);

			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return reviewNo;
	}


	
	//게시글 목록 조회
	public HashMap<String, Object> getReviewList(String no, String pageNum) {

		ArrayList<Review> reviewList = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			String sql = "{call p_get_boardlist(?,?,?,?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, no);
			stmt.setString(2, pageNum);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.INTEGER);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			
			ResultSet rsList = (ResultSet)stmt.getObject(3);
			ResultSet rsMag = (ResultSet)stmt.getObject(5);

			while(rsList.next()) {
				
				Review r = new Review();
				r.setReviewNo(rsList.getString("review_no"));
				r.setTitle(rsList.getString("title"));
				r.setContent(rsList.getString("content"));
				r.setCount(rsList.getString("count"));
				r.setWdate(rsList.getString("wdate"));
				r.setMdate(rsList.getString("mdate"));
				r.setWriter(rsList.getString("email"));
				
				reviewList.add(r);
			}
			
			while(rsMag.next()) {
				for(Review r : reviewList) {
					if(r.getReviewNo().equals(rsMag.getString("review_no"))) {
						r.setRecommend(rsMag.getInt("recommend"));
						r.setReport(rsMag.getInt("report"));
					}
				}
			}
			
			rsList.close();
			rsMag.close();
			
			Page page = new Page(Integer.parseInt(pageNum), stmt.getInt(4));
			
			map.put("reviewList", reviewList);
			map.put("page", page);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return map;
	}


	
	public Review getReviewContent(String no, String email) {
		//리뷰 세부 내용 조회
		
		String sql = "{call p_get_review_content(?,?,?,?,?,?)}";
		Review r = new Review();
		ArrayList<Reply> replyList = new ArrayList<>();
		
		r.setReplyList(replyList);
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, no);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.setString(4, email);
			stmt.registerOutParameter(5, OracleTypes.INTEGER);
			stmt.registerOutParameter(6, OracleTypes.INTEGER);
			
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			int bookmarkCheck = stmt.getInt(3);
			int recommend = stmt.getInt(5);
			int report  = stmt.getInt(6);
			
			r.setBookMarkCheck(bookmarkCheck);
			r.setRecommend(recommend);
			r.setReport(report);
			
			if(rs.next()) {
				
				//리뷰 세부 내용 dto 셋팅
				
				r.setTitle(rs.getString("title"));
				r.setContent(rs.getString("r_content"));
				r.setCount(rs.getString("count"));
				r.setWdate(rs.getString("r_wdate"));
				r.setWriter(rs.getString("r_email"));
				r.setReviewNo(rs.getString("r_review_no"));
				r.setHospitalNo(rs.getString("hospitalno"));
				r.setMdate(rs.getString("r_mdate"));
				r.setReviewScore(rs.getString("score"));
				
				if(rs.getInt("filesize") > 0) {
					
					//리뷰 첨부파일 내용 dto 셋팅

					
					FileAttached f = new FileAttached();
					f.setFileName(rs.getString("filename"));
					f.setFileNameSave(rs.getString("filenamesave"));
					f.setFileNo(rs.getString("fileno"));
					f.setFilePath(rs.getString("filepath"));
					f.setFileSize(rs.getString("filesize"));
					f.setFileType(rs.getString("filetype"));
					f.setReviewNo(rs.getString("r_review_no"));
					f.setWdate(rs.getString("f_wdate"));
					
					r.setFileAttached(f);
				}
				
				do {
					
					if(rs.getString("replyno") != null) {
						
						//리뷰 댓글 내용 dto 셋팅
						
						Reply p = new Reply();
						
						p.setReviewNo(rs.getString("r_review_no"));
						p.setReplyNo(rs.getString("replyno"));
						p.setEmail(rs.getString("p_email"));
						p.setContent(rs.getString("p_content"));
						p.setWdate(rs.getString("p_wdate"));
						
						replyList.add(p);
					}
					
				}while(rs.next());
			}
			
			rs.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;
	}

	
	//review 수정
	public String updateBoard(Review r) {
		
		FileAttached f = r.getFileAttached();
		String rs = null;
		
		try {
			String sql = "{call  p_update_review(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, r.getReviewNo());
			stmt.setString(2, r.getTitle());
			stmt.setString(3, r.getContent());
			stmt.setString(4, r.getReviewScore());
			stmt.setString(5, f.getFileName());
			stmt.setString(6, f.getFileNameSave());
			stmt.setString(7, f.getFileSize());
			stmt.setString(8, f.getFileType());
			stmt.setString(9, f.getFilePath());
			
			int stat = stmt.executeUpdate();
			
			if(stat>0) {
				rs = r.getReviewNo();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}




	//게시글 삭제
	public int delReview(String reviewNo, HttpSession session) {
		
		int stat = 0;
		
		try {
			String sql ="{call p_del_review(?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, reviewNo);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stat = stmt.executeUpdate();
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			if(rs.next()) {
				delAttachFile(session, rs);
			}
			
			stat = 1;
			
			rs.close();
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return stat;
	}


	//북마크 리스트 출력
	public ArrayList<Review> getBookMarkList(String email) {
		
		ArrayList<Review> bookmarkList = new ArrayList<>();
		
		try {
			String sql = "{call p_get_bookmarkList(?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, email);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			while(rs.next()) {
				
				Review r = new Review();
				
				r.setWriter(rs.getString("email"));
				r.setTitle(rs.getString("title"));
				r.setContent(rs.getString("content"));
				r.setWdate(rs.getString("wdate"));
				r.setCount(rs.getString("count"));
				r.setReviewUrl(rs.getString("url_content"));
				
				bookmarkList.add(r);
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return bookmarkList;
	}


	//북마크 등록 / 수정
	public int bookmarkProc(String review_no, String review_url, String email) {
		
		int rs = 0;
		
		try {
			String sql = "{call p_bookmark_proc(?, ?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, review_no);
			stmt.setString(2, review_url);
			stmt.setString(3, email);
			stmt.registerOutParameter(4, OracleTypes.INTEGER);
			
			stmt.executeUpdate();
			
			rs = stmt.getInt(4);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}

	
	//게시글 검색
	public HashMap<String, Object> searchReview(String select, String condition, String pageNum, String hosNo) {

		ArrayList<Review> reviewList = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			String sql = "{call p_search_review(?, ?, ?, ?, ?, ?) }";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, select);
			stmt.setString(2, condition);
			stmt.setString(3, pageNum);
			stmt.setString(4, hosNo);
			stmt.registerOutParameter(5, OracleTypes.INTEGER);
			stmt.registerOutParameter(6, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(6);

			while(rs.next()) {
	
				Review r = new Review();
				
				r.setReviewNo(rs.getString("review_no"));
				r.setWriter(rs.getString("email"));
				r.setTitle(rs.getString("title"));
				r.setContent(rs.getString("content"));
				r.setCount(rs.getString("count"));
				r.setWdate(rs.getString("wdate"));
				r.setMdate(rs.getString("mdate"));
				
				reviewList.add(r);
			}
			
			Page page = new Page(Integer.parseInt(pageNum), stmt.getInt(5));
			
			map.put("reviewList", reviewList);
			map.put("page", page);
			
			rs.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return map;
	}


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
				
				rs.close();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return noticeList;
		
	}


	//공지사항 내용 보기
	public Notice getNoticeContent(String noticeNo) {
	
		Notice n = new Notice();
		
		try {
			String sql = "{call p_get_notice_content(?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, noticeNo);
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
				
				rs.close();
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return n;
	
	}


	//index 페이지에 띄울 notice 리스트 가져오기
	public ArrayList<Notice> getNoticeListLimit() {
		
		ArrayList<Notice> noticeList = new ArrayList<>();
		
		try {
			String sql = "{call p_get_noticelist_top5(?)}";
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
				
				rs.close();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return noticeList;

	}


	
	//sns 리뷰 리스트 가져오기
	public ArrayList<SnsReview> getSnsReviewList(String hospitalNo) {
		
		ArrayList<SnsReview> snsReviewList = new ArrayList<>();
		String sql = "{call p_get_sns_list(?,?)}";
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, hospitalNo);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			while(rs.next()) {
				
				SnsReview sr = new SnsReview();
				sr.setEmail(rs.getString("email"));
				sr.setSnsContent(rs.getString("snscontent"));
				sr.setWdate(rs.getString("wdate"));
				sr.setSnsReviewNo(rs.getString("snsreno"));
				
				snsReviewList.add(sr);
				
				if(rs.getString("filename") != null) {
					FileAttached f = new FileAttached();
					f.setFileName(rs.getString("filename"));
					f.setFileNameSave(rs.getString("filenamesave"));
					f.setFileSize(rs.getString("filesize"));
					f.setFileType(rs.getString("filetype"));
					f.setFilePath(rs.getString("filepath"));
					
					sr.setFileAttached(f);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return snsReviewList;
	}



	//sns형태 board insert
	public int insertSnsBoard(SnsReview sr, String hospitalNo) {
		
		String sql = "{call p_insert_snsReview(?, ?, ?, ?, ?, ?, ?,?)}";
		
		int rs = 0;
		FileAttached f = sr.getFileAttached();
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			
			stmt.setString(1, hospitalNo);
			stmt.setString(2, sr.getSnsContent());
			stmt.setString(3, sr.getEmail());
			stmt.setString(4, f.getFileName());
			stmt.setString(5, f.getFileNameSave());
			stmt.setString(6, f.getFileSize());
			stmt.setString(7, f.getFileType());
			stmt.setString(8, f.getFilePath());
			
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}


	
	//sns게시판 review 삭제
	public int delSnsReview(String snsNo, HttpSession session) {
		
		int stat = 0;
		try {
			String sql = "{call p_del_snsReview(?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, snsNo);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stat = stmt.executeUpdate();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			if(rs!=null) {
				while(rs.next()){
					delAttachFile(session, rs);				
				}				
			}
			
			stat = 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return stat;
	}
	
	
	//snsReview수정
	public String updateSnsReview(SnsReview snsReview, HttpSession session) {
		
		FileAttached f = snsReview.getFileAttached();
		String stat = null;
		
		String sql = "{call p_updatesnsReview(?, ?, ?, ?, ?, ?, ?, ?)}";
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, snsReview.getSnsReviewNo());
			stmt.setString(2, snsReview.getSnsContent());
			stmt.setString(3, f.getFileName());
			stmt.setString(4, f.getFileNameSave());
			stmt.setString(5, f.getFileSize());
			stmt.setString(6, f.getFileType());
			stmt.setString(7, f.getFilePath());
			stmt.registerOutParameter(8, OracleTypes.CURSOR);
			
			stmt.executeUpdate();
			
			ResultSet rs = (ResultSet)stmt.getObject(8);
			if(rs != null && rs.next()) {
				delAttachFile(session, rs);
			}
			
			stat = "1";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stat;
	}

	
	
	
	
	//병원 리스트 출력
	public HashMap<String, Object> getHospitalList(String location) {
	
		//return 할 객체
		HashMap<String, Object> result = new HashMap<>();
		
		//병원 정보를 담기 위해 사용하는 객체
		ArrayList<Hospital> hospitalList = new ArrayList<>();
		ArrayList<Code> hospitalCodeList = null;
		String prvHosNo = null;		
		
		try {
			String sql ="{call p_get_hospital_user(?,?,?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			
			stmt.setString(1, location);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);

			stmt.executeQuery();
			
			ResultSet rsHospital = (ResultSet)stmt.getObject(2);
			ResultSet rsCode = (ResultSet)stmt.getObject(3);
			
			
//			병원 정보를 저장
			while(rsHospital.next()) {
				
				//이전 병원 번호와 현재 병원 번호가 같지 않을 때 새로운 hospital 객체 생성
				if(!rsHospital.getString("hospitalno").equals(prvHosNo)) {
					
					Hospital h = new Hospital();
					
					//hospital에 연결해줄 병원태그 arrayList 초기화(갱신)
					//hospital No가 바뀔 때마다 새로운 객체로 갱신되게 한다.
					hospitalCodeList = new ArrayList<>();
					
					
					h.setHospitalNo(rsHospital.getString("hospitalno"));
					h.setHospitalName(rsHospital.getString("hospitalname"));
					h.setHospitalTel(rsHospital.getString("hospitaltel"));
					h.setPost(rsHospital.getString("post"));
					h.setHospitalAdd1(rsHospital.getString("hospitaladd1"));
					h.setHospitalAdd2(rsHospital.getString("hospitaladd2"));
					h.setHospitalAdd3(rsHospital.getString("hospitaladd3"));
					h.setScore(rsHospital.getString("score"));
					
					h.setCode(hospitalCodeList);
					
				
					hospitalList.add(h);
					
					//next한 hospitalNo와 비교하기 위해 저장
					prvHosNo = h.getHospitalNo();
					
				}
				
				//병원 태그는 rs.next가 true일 때 마다 매번 저장해야한다.
				//따라서 생성되어있는 arrayList에 지속적으로 add해줌.
				
				Code c = new Code();
				
				c.setCategory("병원태그");
				c.setCodeName(rsHospital.getString("codename"));
				c.setCodeValue(rsHospital.getString("codevalue"));
				
				hospitalCodeList.add(c);
				
				result.put("hospital", hospitalList);
			}
		
			
			//전체 codelist를 저장할 객체
			ArrayList<Code> totalCodeList = new ArrayList<>();
			
			
			//전체 코드 리스트를 저장
			while(rsCode.next()) {
				Code code = new Code();
				code.setCategory("병원태그");
				code.setCodeNo(rsCode.getString("codeno"));
				code.setCodeName(rsCode.getString("codename"));
				code.setCodeValue(rsCode.getString("codevalue"));
				
				totalCodeList.add(code);
			}
			
			result.put("codeList", totalCodeList);
			
			rsHospital.close();
			rsCode.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	

	//덧글 등록
	public int insertReply(Reply r) {
		
		String sql = "INSERT INTO reply (replyno, reviewno, email, content) VALUES "
				   + " (replyno.nextval, ?, ?, ?)";
		
		int rs = 0;
		
		try {
			PreparedStatement stmt = dbconn.prepareStatement(sql);
			stmt.setString(1, r.getReviewNo());
			stmt.setString(2, r.getEmail());
			stmt.setString(3, r.getContent());
			
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return rs;
	}

	
	//덧글삭제
	public int deleteReply(int replyNo) {
		
		String sql = "DELETE FROM reply WHERE replyno = ?";
		int rs = 0;
		
		try {
			PreparedStatement stmt = dbconn.prepareStatement(sql);
			
			stmt.setInt(1, replyNo);
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}


	
	//덧글 수정
	public int updateReply(int replyNo, String content) {
		
		String sql = "UPDATE reply SET content = ?, mdate = CURRENT_TIMESTAMP WHERE replyno = ?";
		int rs = 0;
		
		try {
			PreparedStatement stmt = dbconn.prepareStatement(sql);
			
			stmt.setString(1, content);
			stmt.setInt(2, replyNo);
			
			rs = stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return rs;
	}


	
	//리뷰 추천
	public int recommendReview(String sessionId, String reviewNo) {
		
		String sql = "INSERT INTO reviewmag(revmagno, from_mem, to_review, magcode)"
				   + " VALUES (REVMAGNO.nextval, ?, ?, 1)";
		
		int rs = 0;
		
		try {
			PreparedStatement stmt = dbconn.prepareStatement(sql);
			
			stmt.setString(1, sessionId);
			stmt.setString(2, reviewNo);
			
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return rs;
	}

	
	//리뷰 신고
	public int reviewReport(String sessionId, String reviewNo) {
		
		String sql = "INSERT INTO reviewmag(revmagno, from_mem, to_review, magcode)"
				   + " VALUES (REVMAGNO.nextval, ?, ?, 2)";
		
		int rs = 0;
		
		try {
			PreparedStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, sessionId);
			stmt.setString(2, reviewNo);
			
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		
		return rs;
	}


	//병원 검색
	public HashMap<String, Object> searchHospital(String[] hospitalSearchCondition, String location) {
		
		HashMap<String, Object> result = new HashMap<>();
		
		String sql = "{call p_get_search_hospital(?,?,?,?)}";
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			
			//DB에 생성한 사용자 지정 type 정보 설정. 타입 이름은 대문자로 적어준다(소문자로 작성시 오류 발생)
			ArrayDescriptor descripotor = ArrayDescriptor.createDescriptor("T_VARCHAR2_ARRAY", dbconn);
			
			//해당 타입과 전달할 배열 연결
			ARRAY array_to_pass = new ARRAY(descripotor, dbconn, hospitalSearchCondition);
			
			stmt.setArray(1, array_to_pass);
			stmt.setString(2, location);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			
			ResultSet rsHospital = (ResultSet)stmt.getObject(3);
			ResultSet rsCode = (ResultSet)stmt.getObject(4);
			
			String prvHospital = "0";
			
			ArrayList<Hospital> hospitalList = new ArrayList<>();
			ArrayList<Code> hospitalTag = new ArrayList<>();
			
			while(rsHospital.next()) {
				if(!prvHospital.equals(rsHospital.getString("hospitalno"))) {
					
					Hospital hospital = new Hospital();
					hospitalTag = new ArrayList<>();
					
					hospital.setHospitalNo(rsHospital.getString("hospitalno"));
					hospital.setHospitalName(rsHospital.getString("hospitalname"));
					hospital.setHospitalTel(rsHospital.getString("hospitaltel"));
					hospital.setPost(rsHospital.getString("post"));
					hospital.setHospitalAdd1(rsHospital.getString("hospitaladd1"));
					hospital.setHospitalAdd2(rsHospital.getString("hospitaladd2"));
					hospital.setHospitalAdd3(rsHospital.getString("hospitaladd3"));
					hospital.setWdate(rsHospital.getString("wdate"));
					hospital.setScore(rsHospital.getString("score"));
					hospital.setCode(hospitalTag);;
					
					hospitalList.add(hospital);
					
					prvHospital = hospital.getHospitalNo();
				}
				
				Code code = new Code();
				
				code.setCategory("병원태그");
				code.setCodeName(rsHospital.getString("codename"));
				code.setCodeValue(rsHospital.getString("codevalue"));
				
				hospitalTag.add(code);
			}
			
			
			ArrayList<Code> codeList = new ArrayList<>();
			
			while(rsCode.next()) {
				Code c = new Code();
				c.setCategory(rsCode.getString("category"));
				c.setCodeNo(rsCode.getString("codeno"));
				c.setCodeName(rsCode.getString("codename"));
				c.setCodeValue(rsCode.getString("codevalue"));
				
				codeList.add(c);
			}
			
			result.put("hospital", hospitalList);
			result.put("code", codeList);
			
			rsHospital.close();
			rsCode.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	//첨부파일 삭제
	//filedao와 겹쳐지는 부분. 하지만 코드가 긴 게 아니라 따로 빼서 가져갈지, 그냥 둘지 고민중...
	public void delAttachFile(HttpSession session, ResultSet rs) throws SQLException {		
		ServletContext ctx = session.getServletContext();
		String path = ctx.getRealPath("resources/upload");
		
		File file = new File(path, rs.getString("filenamesave"));
		
		if(file.exists()) {
			file.delete();
			
		}
	
	}










}
