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
<!-- 부트스트랩 공부할거면 해보고.. 아님 말고....-->
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->
<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<style>
	
	a#boardSeoul{
		color: #ffffff !important;
	}
	
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
		<c:set value="${review }" var="r"/>
			<br>
			<p id="page_infor">＠ 서울시 _ OO 동물 병원</p>
			<p id="title_cont"><b>Title</b> : ${r.title}</p>
			<c:if test="${sess_id != null }">
				<i id="bookmark" class="material-icons" onclick="bookmark(${r.reviewNo})">
					<c:if test="${r.bookMarkCheck == 1 }">
						bookmark
					</c:if>
					<c:if test="${r.bookMarkCheck == 0 }">
						bookmark_border
					</c:if>
				</i>
			</c:if>
			<p id="email_cont"><b>Email</b>: ${r.writer}</p>
			<p id="date_cont"><b>Date</b> : ${r.wdate}</p>
			<p id="date_cont"><b>ModDate</b> : ${r.mdate}</p>
			<div id = "content_view">
			${r.content}
			</div>
				
			<c:if test="${ sess_id != null }">
				<c:if test="${sess_id eq r.writer }">
					<form method="POST" action="/board/boardModForm">
						<input type="hidden" value="${r.reviewNo }"  name="reviewNo">
						<input type="hidden" value="${r.title}"  name="title">
						<input type="hidden" value="${r.content}"  name="content">
						<input type="hidden" value="${r.writer}"  name="writer">
						<input type="hidden" value="${r.wdate}"  name="wdate">
						<input type="hidden" value="${r.mdate}"  name="mdate">
						<c:if test="${r.fileAttached != null }">
							<input type="hidden" value="${r.fileAttached.fileName }"  name="fileName">
							<input type="hidden" value="${r.fileAttached.fileNameSave }"  name="fileNameSave">
							<input type="hidden" value="${r.fileAttached.fileType }"  name="fileType">
							<input type="hidden" value="${r.fileAttached.fileSize }"  name="fileSize">
						</c:if>
						<input id="modify" type="submit" value="수정">
			
					</form>
		
					<form method="POST" action="/board/boardDel" onsubmit="return checkYN()">					
						<input type="hidden" value="${r.reviewNo }"  name="reviewNo">
						<input id="del" type="submit" value="삭제">
					</form>
		
				</c:if>
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
		
	 </div>	
	
<%@include file="../footer_pj.jsp" %>

	
</body>
</html>