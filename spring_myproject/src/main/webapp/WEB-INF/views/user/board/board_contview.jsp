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
<link rel="stylesheet" href="/css_file/contentView_css.css">
<script src="/js_file/main_home_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Material+Icons" rel="stylesheet">
<style>
	
	
	div#area_drop{
		display: block !important;
	}

</style>

<title>동물병원 통합 아카이빙 시스템 : 서울 - OO 동물병원</title>
</head>

<!-- 온로드 로그인상태 체크 함수 -->
<body>


<script>
function checkYN(){
	if(confirm("삭제하시겠습니까?")){
		 return true;
	}else{
		return false;
	}
}


window.onload = function(){
	
// 	1이상일 시 리플 수정 성공
	if(document.getElementById('replyStat').value > 0){
		alert('수정완료');		
	}
	
}


</script>

	
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
		<div id="main_right_column">
		

<!-- 			리플 수정 상태 체크 -->
			<input type="hidden" id="replyStat" value="${replyStat }">


<!-- 			리뷰 내용 본문 div -->
			<div id="reviewContent">
			<c:set value="${review }" var="r"/>
			
				<input type="button" id="goList" value="목록" onclick="document.forms['reviewListForm${r.reviewNo }'].submit()">
				<br>
				<c:set value="${hospital }" var="h"/>
				
	<!-- 			리뷰 목록으로 되돌아가기용 form -->
				<form name="reviewListForm${r.reviewNo }" action="/board/boardReview">
						
					<input name="pageNum" type="hidden" value="${pageNum }">
					<input type="hidden" value="${r.reviewNo }"  name="reviewNo">
					<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
					<input name="hospitalName" type="hidden" value="${h.hospitalName }">
					<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
					<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
					<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
					<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
					
					<c:forEach items="${h.hostag }" var="tag" varStatus="status">
						<input name="hostag" type="hidden" value="${tag }">										
					</c:forEach>
							
				</form>
				
				
				<p id="page_infor">＠ ${h.hospitalName }</p>
				<p id="page_infor">＠ ${h.hospitalAdd1 }, ${h.hospitalAdd2 }</p>
				
				<p id="title_cont"><b>Title</b> : ${r.title} </p>
				
				<c:if test="${sess_id != null }">
					<i id="bookmark" class="material-icons" onclick="bookmark('${r.reviewNo}')">
						<c:if test="${r.bookMarkCheck == 1 }">
							bookmark
						</c:if>
						<c:if test="${r.bookMarkCheck == 0 }">
							bookmark_border
						</c:if>
					</i>
				</c:if>
				
				<p id="email_cont" onclick="userReportDivPlz()"><a><b>Email</b>: ${r.writer}</a></p>
				
<!-- 				회원 추천 신고  -->
				<div id="userRecommOrReport">
					<a onclick="userRecommend('${sess_id }','${r.writer }')">회원 추천</a> <b>/</b>
					<a onclick="userReport('${sess_id }','${r.writer }')">회원 신고</a>
				</div>
				
				<p id="date_cont"><b>Date</b> : ${r.wdate}</p>
				<p id="date_cont"><b>ModDate</b> : ${r.mdate}</p>
				<div id = "content_view">
				${r.content}
				</div>
					
					
	<!-- 				리뷰 수정용 폼 -->
				<c:if test="${ sess_id != null }">
					<c:if test="${sess_id eq r.writer }">
						<form method="POST" action="/board/boardModForm">
						
							<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
							<input name="hospitalName" type="hidden" value="${h.hospitalName }">
							<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
							<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
							<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
							<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
								
							<c:forEach items="${h.hostag }" var="tag" varStatus="status">
								<input name="hostag" type="hidden" value="${tag }">										
							</c:forEach>
							
							
							<input type="hidden" value="${r.reviewNo }"  name="reviewNo">
							<input type="hidden" value="${r.title}"  name="title">
							<input type="hidden" value="${r.content}"  name="content">
							<input type="hidden" value="${r.writer}"  name="writer">
							<input type="hidden" value="${r.wdate}"  name="wdate">
							<input type="hidden" value="${r.mdate}"  name="mdate">
							<input type="hidden" value="${pageNum}"  name="pageNum">
							
							
							<c:if test="${r.fileAttached != null }">
								<input type="hidden" value="${r.fileAttached.fileName }"  name="fileName">
								<input type="hidden" value="${r.fileAttached.fileNameSave }"  name="fileNameSave">
								<input type="hidden" value="${r.fileAttached.fileType }"  name="fileType">
								<input type="hidden" value="${r.fileAttached.fileSize }"  name="fileSize">
							</c:if>
							<input id="modify" type="submit" value="수정">
				
						</form>
			
			
	<!-- 					리뷰 삭제용 폼 -->
						<form method="POST" action="/board/boardDel" onsubmit="return checkYN()">					
							<input name="reviewNo" type="hidden" value="${r.reviewNo }">
							<input name="pageNum" type="hidden" value="${pageNum }">
							<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
							<input name="hospitalName" type="hidden" value="${h.hospitalName }">
							<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
							<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
							<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
							<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
								
							<c:forEach items="${h.hostag }" var="tag" varStatus="status">
								<input name="hostag" type="hidden" value="${tag }">										
							</c:forEach>
									
							
							<input id="del" type="submit" value="삭제">
						</form>
			
					</c:if>
				</c:if>
				
<!-- 				리뷰 추천 / 신고 -->
				<c:if test="${ sess_id != null }">
					<table cellspacing="0" id="recommOrReport">
						<tr>
							<td>
								<i class="material-icons" style="color:#4d5966" onclick="reviewRecommend('${sess_id }','${r.reviewNo }')">thumb_up</i>
							</td>
													
							<td id="reportIcon">
								<i class="material-icons" style="color:#4d5966" onclick="reviewReport('${sess_id }','${r.reviewNo }')">thumb_down</i>
							</td>
						</tr>
					</table>
				</c:if>
				
				
				<c:set value="${r.fileAttached }" var="f"/>
				<div id="file_view">
					<c:if test="${r.fileAttached != null }">
						<br>
						<p><b>첨부파일명:</b> ${f.fileName }</p>
						<p><b>사이즈:</b> ${f.fileSize }</p>
						<c:if test="${fn:contains(f.fileType, 'image') }">
							<img src="/upload/${f.fileNameSave }">	
						</c:if>
						
						<form name="fileDown" method="post" action="/file/download">
							<input type="hidden" name="fileNameSave" value="${f.fileNameSave }">
							<input type="hidden" name="filePath" value="${f.filePath }">
							<br><input type="submit" value="다운로드">
						</form>
			
					</c:if>
				</div>
			</div>
			
			
<!-- 			리뷰 댓글 table/form -->
			<c:if test="${sess_nickname != null}">
				<form name="replyReg" id="replyReg" method="POST" onsubmit="return replyRegProc()">
					<table id="replyRegTable" cellspacing="0">
						<tr>
							<td>
								<b>${sess_nickname }</b> :
								<textarea name="content" id="replyContent" required maxlength="500" ></textarea>		
							</td>
							<td>
								<input name="email" type="hidden" value="${sess_id }">
								<input name="reviewNo" type="hidden" value="${r.reviewNo }">
								<input id="replySubmit" type="submit" value="등록">				
							</td>
						</tr>
					</table>
				</form>
			</c:if>
			
			<div id="replyListDiv">
				<table id="replyList" cellspacing="0">
					<c:forEach items="${r.replyList }" var="p">
						<tr>
							<td>
								<a id="replyEmail" onclick="replyUserRecommOrReportPlz('${p.replyNo }')"><b>${p.email }</b></a> / ${p.wdate }
								
								<div class="replyUserRecommOrReport" id="replyUserRecommOrReport${p.replyNo }">
									<a onclick="userRecommend('${sess_id }','${p.email }')">회원 추천</a> <b>/</b>
									<a onclick="userReport('${sess_id }','${p.email }')">회원 신고</a>
								</div>
								
								
								<div id="replyListContent">${p.content }</div>
								
<!-- 								리플 수정용 폼 -->
								<form name="replyModForm" action="/board/reviewModProc">
								
									<input name="reviewNo" type="hidden" value="${r.reviewNo }">
									<input name="pageNum" type="hidden" value="${pageNum }">
									<input Name="replyNo" type="hidden" value="${p.replyNo }">
									
									<textarea name="content" id="replyModContent" required></textarea>
									
									<input name="hospitalNo" type="hidden" value="${h.hospitalNo }">
									<input name="hospitalName" type="hidden" value="${h.hospitalName }">
									<input name="hospitalAdd1" type="hidden" value="${h.hospitalAdd1 }">
									<input name="hospitalAdd2" type="hidden" value="${h.hospitalAdd2 }">
									<input name="hospitalAdd3" type="hidden" value="${h.hospitalAdd3 }">
									<input name="hospitalTel" type="hidden" value="${h.hospitalTel }">
									
								</form>
							</td>
							<c:if test="${sess_id != null }">
								<c:if test="${p.email eq sess_id }">
									<td style="width:60px;">
										<button id="replyModFormShowButton" onclick="replyModFormShow('${p.content }')">수정</button>
										<button id="replyModFormSubmit" onclick="document.forms['replyModForm'].submit()"> 수정</button>
									</td>
									<td style="width:60px;">
										<button onclick="replyDelProc('${p.replyNo }')">삭제</button>
									</td>
								</c:if>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				
			</div>
			
		</div>
		
	 </div>	
	
<%@include file="../footer_pj.jsp" %>

	
</body>
</html>