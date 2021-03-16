<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<link rel="stylesheet" href="/css_file/admin_css.css">
<link rel="stylesheet" href="/css_file/board_notice_css.css">
<script src="/js_file/admin_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
<!-- 부트스트랩 공부할거면 해보고.. 아님 말고....-->
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->
<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>동물병원 통합 아카이빙 시스템 : 공지사항</title>
</head>

<!-- 온로드 로그인상태 체크 함수 -->
<body>

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
			<form name="board_write" method="POST" enctype="multipart/form-data" action="/admin/noticeWriteProc">
				<p id="title_cont"><b>Title</b> : <input type="text" name="title" required maxlength="13"><br>
				<p id="email_cont"><b>Writer </b> : ${gradeName }<br>
				<textarea id="cont_input" name="content" placeholder="내용" required></textarea><br>
				<input id="file_input" type="file" name="fileAttach"><br>
				<input id="reg" type="submit" value="등록">
			</form>
		</div>
		
	 </div>	
	
<%@include file="../footer_pj.jsp" %>

	
</body>
</html>