<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


        


<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<link rel="stylesheet" href="/css_file/main_home_css.css">
<link rel="stylesheet" href="/css_file/board_table_css.css">
<script src="/js_file/main_home_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
﻿<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<style>
	
	
	div#area_drop{
		display: block !important;
	}

</style>



<title>동물 병원 후기 모음집 : 리뷰</title>
</head>

<body>
<script>

	window.onload=function(){
		
		if(document.getElementById("delStat").value == 1){
			alert('게시글이 삭제되었습니다.');
		}
		
	}

</script>

<input type="hidden" value="${delStat }" id="delStat">

	
<!-- 로그인 및 회원가입창 -->
<%@include file="../member/login_join_modal.jsp" %>		

<!-- 헤더영역 -->
<%@include file="../header_pj.jsp" %>	

<!-- 미니메뉴 영역 -->
<p id="welcom">
<c:if test="${sess_nickname != null}">
<b>${sess_nickname }</b>님 환영합니다.
</c:if>
</p>
<%@include file="../navbar_mini.jsp" %>
					

<!-- 메인내용(왼쪽 오른쪽 컬럼)		 -->
	 <div id="main_content">
		
<!-- 			왼쪽 메뉴 영역	 -->
		<%@include file="../navbar_left.jsp" %>

<!-- 메인 오른쪽 컬럼		 -->
		<div id="main_right_column_board">
		
			<c:set value="${hospital }" var="h"/>
			
			<div id="board_search_box">
			
<!-- 			검색용 폼 -->
				<form name="searchForm" action="/board/reviewSearch" onsubmit="return selectCeck()">
					<select name="search" required>
						
						<option value="none"
						<c:if test="${select eq 'none' }"> selected</c:if>
						>분류</option>
						<option value="email"
						<c:if test="${select eq 'email' }"> selected</c:if>
						>작성자</option>
						<option value="title"
						<c:if test="${select eq 'title' }"> selected</c:if>
						>제목</option>
						<option value="content"
						<c:if test="${select eq 'content' }"> selected</c:if>
						>내용</option>
					</select>
					
					<input id="board_search" name="condition" type="text" placeholder="내용을 입력하세요" required 
					value="${condition }"
					maxlength="50">
					
					<input name="pageNum" type="hidden" value="1">
					
					<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
					<input name="hospitalName" type="hidden" value="${h.hospitalName }">
					<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
					<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
					<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
					<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
					<input type="hidden" name="score" value="${h.score }">
					
					<c:forEach items="${h.hostag }" var="tag">
						<input name="hostag" type="hidden" value="${tag }">
					</c:forEach>
					
					
					<button id="board_search_confirm" ><i class="fa fa-search"></i></button>
				</form>
				
				
<!-- 				페이징용 폼 -->
				<form name="pagingForm" action="/board/boardReview">
					
					<input name="pageNum" type="hidden" value="1">
					<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
					<input name="hospitalName" type="hidden" value="${h.hospitalName }">
					<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
					<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
					<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
					<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
					<input type="hidden" name="score" value="${h.score }">
					
					<c:forEach items="${h.hostag }" var="tag">
						<input name="hostag" type="hidden" value="${tag }">
					</c:forEach>
					
				</form>
				
			</div>
						
						
						
			<div id="hospital_view">
				<img id="hospitalImg" src="/img/hospital.png" alt="병원이미지">
				<p id="hosName" ><b>${h.hospitalName }</b></p>
				<p>${h.hospitalAdd1 }, ${h.hospitalAdd2 } ${h.hospitalAdd3 }</p>
				<p><b>연락처</b> : ${h.hospitalTel }</p>
				<p><b>별점</b> : ${h.score }</p>
				
				<p>
					<c:forEach items="${h.hostag }" var="tag" varStatus="status">
						${tag }
						<c:if test="${not status.last }"><b>/</b>
						</c:if>
					</c:forEach>
				</p>
				
			</div>
			<p id="pageInfor"><b>${page.thisPage }</b> 페이지</p>
			<div id="board_list">
				<table id="boardReviewList" cellspacing="0">
				 	<thead>
						<tr>
							<td class="num">
								<b>순번</b>
							</td>
							<td class="title">
								<b>제목</b>
							</td>
							<td class="date">
								<b>작성일</b>
							</td>
							<td class="writer">
								<b>작성자</b>
							</td>
							<td class="cnt">
								<b>추천수</b>
							</td>
							<td class="cnt">
								<b>신고수</b>
							</td>
							<td class="cnt">
								<b>조회수</b>
							</td>
						</tr>
					</thead>
					
					<c:set value="${fn:length(reviewList)+1 }" var="cnt"/>
					<c:forEach items ="${ reviewList}" var="list">
						<c:set value="${cnt-1 }" var="cnt"/>
						<tbody>
						
<!-- 						리뷰 내용 조회용 폼 -->
							<tr onclick="document.forms['reviewContentForm${list.reviewNo }'].submit()">
								
								<form name="reviewContentForm${list.reviewNo }" action="/board/board_contview">
					
									<input name="pageNum" type="hidden" value="${page.thisPage }">
									<input name="seqno_r" type="hidden" value="${list.reviewNo }">
									<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
									<input name="hospitalName" type="hidden" value="${h.hospitalName }">
									<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
									<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
									<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
									<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
									<input type="hidden" name="score" value="${h.score }">
									
									<c:forEach items="${h.hostag }" var="tag" varStatus="status">
										<input name="hostag" type="hidden" value="${tag }">										
									</c:forEach>
									
									
								</form>
												
								
								<td>
									${cnt }
								</td>
								<td>
									${list.title }
								</td>
								<td>
									${list.wdate }
								</td>
								<td>
									${list.writer }
								</td>
								<td>
									${list.recommend }
								</td>
								<td>
									${list.report }
								</td>
								<td>
									${list.count }
								</td>
							</tr>
						</tbody>	
					</c:forEach>	
					
				</table>
			</div>

			<c:if test="${sess_id != null }">	
				<!-- 			리뷰 작성용 form -->
				<form name="reviewRegForm" action="/board/boardRegForm">
						
					<input name="pageNum" type="hidden" value="${page.thisPage }">
					<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
					<input name="hospitalName" type="hidden" value="${h.hospitalName }">
					<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
					<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
					<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
					<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
					<input type="hidden" name="score" value="${h.score }">
					
					<c:forEach items="${h.hostag }" var="tag" varStatus="status">
						<input name="hostag" type="hidden" value="${tag }">										
					</c:forEach>
							
				</form>
			
				
				<button onclick="document.forms['reviewRegForm'].submit()">작성</button>
				
			</c:if>

			<p id="pageUpdate1"></p>
			<div id="pageList">
				<%@include file="board_page.jsp" %>
			</div>

		</div>
			
	 </div>	


	
<%@include file="../footer_pj.jsp" %>


	

	
	
	
</body>
</html>