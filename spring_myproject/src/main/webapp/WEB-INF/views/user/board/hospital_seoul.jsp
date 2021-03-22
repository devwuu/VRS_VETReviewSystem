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
<link rel="stylesheet" href="/css_file/board_seoul_css.css">
<script src="/js_file/main_home_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<style>
	
	a#boardSeoul{
		color: #ffffff !important;
	}
	
	div#area_drop{
		display: block !important;
	}

</style>



<title>동물병원 통합 아카이빙 시스템 : 서울</title>
</head>

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
		
<!-- 			왼쪽 메뉴 영역	 -->
		<%@include file="../navbar_left.jsp" %>

<!-- 메인 오른쪽 컬럼		 -->
		<div id="main_right_column_board">
		
			<div id="board_search_box">
				<input id="board_search" type="text" placeholder="SEARCH">
				<input id="board_search_confirm" type="submit" value="확인">
			</div>
			
			<p id="page_infor">＠ 서울시</p>
			<div id="hospital_list">
<!-- 			추후 병원 리스트를 DB에서 가져오는 식으로 바꿔야함. 따라서 서블릿으로 연결-->

				<div id="hospital1" onclick="location.href='/board/board_seoul'">
					<img id="hospital" src="/img/hospital.png" alt="병원이미지">
					<p id="hosName" ><b>OO 동물 병원</b></p>
					<p><b>주소</b> : XXXXX</p>
					<p><b>연락처</b> : XXXX</p>
					<p><b>24시, 토끼 전문</b></p>
				</div>
				<div id="hospital2" onclick="location.href='/board/sns_seoul'">
					<img id="hospital" src="/img/hospital.png" alt="병원이미지">
					<p id="hosName"><b>XX 동물 병원</b></p>
					<p><b>주소</b> : XXXXX</p>
					<p><b>연락처</b> : XXXX</p>
					<p><b>응급실, 강아지 전문</b></p>
				</div>
			</div>

			<div id="board_page">
				<a>◀</a>
				<a>1</a>
				<a>2</a>
				<a>3</a>
				<a>▶</a>
			</div>
			
		</div>
			
	 </div>	


	
<%@include file="../footer_pj.jsp" %>

	

	
	
	
</body>
</html>