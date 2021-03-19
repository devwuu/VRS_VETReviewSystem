package com.bb.user.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.springframework.stereotype.*;

import com.bb.dbconn.DbConn;
import com.bb.user.dto.FileAttached;
import com.bb.user.dto.Notice;
import com.bb.user.dto.Review;
import com.bb.user.dto.SnsReview;

import oracle.jdbc.OracleTypes;



@Repository
public class BoardDao {
	
	private final Connection dbconn = DbConn.getInstanceOf().oracleConn();

	
	
	//리뷰 등록
	public String insertBoard(Review r) {
		
		String reviewNo = null;
		FileAttached f = r.getFileAttached();
		
		String sql = "{call p_insert_review(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, r.getWriter());
			stmt.setString(2, r.getTitle());
			stmt.setString(3, r.getContent());
			stmt.setString(4, f.getFileName());
			stmt.setString(5, f.getFileNameSave());
			stmt.setString(6, f.getFileSize());
			stmt.setString(7, f.getFileType());
			stmt.setString(8, f.getFilePath());
			stmt.registerOutParameter(9, OracleTypes.INTEGER);
			
			stmt.executeUpdate();
			
			reviewNo = stmt.getString(9);

			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return reviewNo;
	}


	
	//게시글 목록 조회
	public ArrayList<Review> getReviewList() {

		ArrayList<Review> reviewList = new ArrayList<>();
		
		try {
			String sql = "{call p_get_boardlist(?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			ResultSet rs = (ResultSet)stmt.getObject(1);
			
			while(rs.next()) {
				
				Review r = new Review();
				r.setReviewNo(rs.getString("review_no"));
				r.setTitle(rs.getString("title"));
				r.setContent(rs.getString("content"));
				r.setCount(rs.getString("count"));
				r.setWdate(rs.getString("wdate"));
				r.setMdate(rs.getString("mdate"));
				r.setWriter(rs.getString("email"));
				
				reviewList.add(r);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return reviewList;
	}


	
	public Review getReviewContent(String no, String email) {
		//리뷰 세부 내용 조회
		
		String sql = "{call p_get_review_content(?,?,?,?)}";
		Review r = new Review();
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, no);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.setString(4, email);
			
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(2);
			int bookmarkCheck = stmt.getInt(3);
			
			r.setBookMarkCheck(bookmarkCheck);
			
			if(rs.next()) {
				
				r.setTitle(rs.getString("title"));
				r.setContent(rs.getString("content"));
				r.setCount(rs.getString("count"));
				r.setWdate(rs.getString("r_wdate"));
				r.setWriter(rs.getString("email"));
				r.setReviewNo(rs.getString("review_no"));
				r.setMdate(rs.getString("r_mdate"));
				
				if(rs.getInt("filesize") > 0) {
					FileAttached f = new FileAttached();
					f.setFileName(rs.getString("filename"));
					f.setFileNameSave(rs.getString("filenamesave"));
					f.setFileNo(rs.getString("fileno"));
					f.setFilePath(rs.getString("filepath"));
					f.setFileSize(rs.getString("filesize"));
					f.setFileType(rs.getString("filetype"));
					f.setReviewNo(rs.getString("review_no"));
					f.setWdate(rs.getString("f_wdate"));
					
					r.setFileAttached(f);
				}
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
			String sql = "{call  p_update_review(?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, r.getReviewNo());
			stmt.setString(2, r.getTitle());
			stmt.setString(3, r.getContent());
			stmt.setString(4, f.getFileName());
			stmt.setString(5, f.getFileNameSave());
			stmt.setString(6, f.getFileSize());
			stmt.setString(7, f.getFileType());
			stmt.setString(8, f.getFilePath());
			
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
				//filedao와 겹쳐지는 부분. 하지만 코드가 긴 게 아니라 따로 빼서 가져갈지, 그냥 둘지 고민중...
				
				ServletContext ctx = session.getServletContext();
				String path = ctx.getRealPath("resources/upload");
				File file = new File(path, rs.getString("filenamesave"));
				
				if(file.exists()) {
					file.delete();
				}
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
	public ArrayList<Review> searchReview(String select, String condition) {

		ArrayList<Review> reviewList = new ArrayList<>();
		
		
		try {
			String sql = "{call p_search_review(?, ?, ?) }";
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.setString(1, select);
			stmt.setString(2, condition);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(3);
			
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

			rs.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return reviewList;
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
	public ArrayList<SnsReview> getSnsReviewList() {
		
		ArrayList<SnsReview> snsReviewList = new ArrayList<>();
		String sql = "{call p_get_sns_list(?)}";
		
		try {
			CallableStatement stmt = dbconn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(1);
			
			while(rs.next()) {
				
				SnsReview sr = new SnsReview();
				sr.setEmail(rs.getString("email"));
				sr.setSnsContent(rs.getString("snscontent"));
				sr.setWdate(rs.getString("wdate"));
				sr.setSnsReviewNo(rs.getString("snsreno"));
				
				snsReviewList.add(sr);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return snsReviewList;
	}

}
