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
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<title>동물 병원 후기 모음집 : 공지사항</title>
</head>

<!-- 온로드 로그인상태 체크 함수 -->
<body>

	
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
		

	<!-- 왼쪽 메뉴 영역	 -->
		<%@include file="../navbar_left.jsp" %>
	
	<!-- 메인 오른쪽 컬럼		 -->
		<div id="main_right_column">
			<br>
			<c:set value="${notice }" var="n"/>
			<p id="page_infor">＠ 공지사항</p>
			<p id="title_cont"><b>Title</b> : ${n.title }</p>
			<p id="email_cont" style="cursor:text;"><b>Writer</b>: ${n.gradeName }</p>
			<p id="date_cont"><b>wDate</b> : ${n.wdate }</p>
			<p id="date_cont"><b>mDate</b> : ${n.mdate }</p>
			<div id = "content_view">
				${n.content }
			</div>
			
			
			<c:set value="${notice.fileAttached }" var="f"/>
			<div id="file_view">
			
				<c:if test="${f.fileName != null }">
					<br>
					<c:if test="${f.fileName != null }">
						<a>파일이름: ${f.fileName } </a> (size: ${f.fileSize })<br>
						<c:if test="${fn:contains(f.fileType, 'image') }">
							<img src = "/notice/${f.fileNameSave }"
								style="width:auto; height:100px">
								
						</c:if>
						<br>
						
						<form method="POST" action="/file/download">					
						
							<input type="hidden" value="${f.fileNameSave }"  name="fileNameSave">
							<input type="hidden" value="${f.filePath }"  name="filePath">
							<input type="submit" value="다운로드">
						</form>
					</c:if>
				</c:if>
			</div>
		</div>
		
	 </div>	
	
<%@include file="../footer_pj.jsp" %>

	
</body>
</html>