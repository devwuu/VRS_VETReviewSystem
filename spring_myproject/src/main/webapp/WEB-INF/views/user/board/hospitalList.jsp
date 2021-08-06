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
		
			<div id="hospital_search_box">
				<form name="hospitalSearch" method="post" onsubmit="return checkHospitalSearch()" action="/board/searchHospital" >
					<a>태그별 검색: </a>
					<c:forEach items="${codeList }" var="totalC">
						<input name="hospitalSearchCondition" type="checkbox" value="${totalC.codeValue }"> ${totalC.codeName }
					</c:forEach>
					<input name="location" value="${location }" type="text">
					<input id="hospital_search_confirm" type="submit" value="확인">
				</form>
			</div>
			
			<p id="page_infor">＠ ${location }</p>
			<div id="hospital_list">
<!-- 			추후 병원 리스트를 DB에서 가져오는 식으로 바꿔야함. 따라서 서블릿으로 연결-->
				
				<c:forEach items="${hospitalList }" var="h">
					<div id="hospital" onclick="document.forms['hospitalView${h.hospitalNo}'].submit()">
						<img id="hospitalImg" src="/img/hospital.png" alt="병원이미지">
						<p id="hosName" ><b>${h.hospitalName }</b></p>
						<p>${h.hospitalAdd1 }, ${h.hospitalAdd2 } <br> ${h.hospitalAdd3 }</p>
						<p><b>연락처</b> : ${h.hospitalTel }</p>
						<p>
							<c:forEach items="${h.code }" var="c" varStatus="status">
								${c.codeName } 
								<c:if test="${not status.last }"><b>/</b>
								</c:if>
							</c:forEach>
						</p>
						
						<form name="hospitalView${h.hospitalNo }" method="POST" action="/board/boardReview">
							<input type="hidden" name="hospitalNo" value="${h.hospitalNo } ">
							<input type="hidden" name="pageNum" value="1">
							<input type="hidden" name="hospitalTel" value="${h.hospitalTel }">
							<input type="hidden" name="hospitalName" value="${h.hospitalName }">
							<input type="hidden" name="hospitalAdd1" value="${h.hospitalAdd1 }">
							<input type="hidden" name="hospitalAdd2" value="${h.hospitalAdd2 }">
							<input type="hidden" name="hospitalAdd3" value="${h.hospitalAdd3 }">
							<input type="hidden" name="location" value="${location }">
							
							<c:forEach items="${h.code }" var="c" varStatus="status">
								<input type="hidden" name="hostag" value="${c.codeName }">								
							</c:forEach>
							
						</form>
						
					</div>
				</c:forEach>
				
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