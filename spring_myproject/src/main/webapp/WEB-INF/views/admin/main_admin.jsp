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
<script src="/js_file/admin_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>동물병원 통합 아카이빙 시스템 : 관리자</title>
</head>

<!-- 온로드 로그인상태 체크 함수 -->
<body>

<!-- 로그인 실패시 모달창 자동 발생 -->

<!-- 헤더영역 -->
<%@include file="header_pj.jsp" %>	


<!-- 미니메뉴 영역 -->
<p id="welcom">
<c:if test="${sess_nickname != null}">
<b>${sess_nickname }</b>님 환영합니다.
</c:if>
</p>

<%@include file="navbar_mini.jsp" %>

<!-- 메인내용(왼쪽 오른쪽 컬럼)		 -->
	 <div id="main_content">
	 	

	<!-- 			왼쪽 메뉴 영역	 -->
		<%@include file="navbar_left.jsp" %>
	
	<!-- 메인 오른쪽 컬럼		 -->
		<div id="main_right_column">
			<div id="infor_list">
				<p id="infor">Information ▶ </a></p>
				<table cellspacing="0">
				 	<thead>
						<tr>
							<td class="num_infor">
								<b>순번</b>
							</td>
							<td class="title_infor">
								<b>항목</b>
							</td>
							<td class="content_infor">
								<b>내용</b>
							</td>
							<td class="etc_infor">
								<b>비고</b>
							</td>
						</tr>
					</thead>

					<c:set value="${admin }" var="a"/>
						<tr onclick="location.href='/admin/memList'">
							<td class="num_infor">
								1
							</td>
							<td class="title_infor">
								등록 회원
							</td>
							<td class="content_infor">
								<b> ${regMem } </b> 명
							</td>
							<td class="etc_infor">
								-
							</td>
						</tr>
						
					</tbody>
					
					<tbody>
						<tr onclick="location.href='/admin/delMemList'">
							<td class="num">
								2
							</td>
							<td class="title">
								탈퇴 요청 회원
							</td>
							<td class="writer">
								<b> ${delMem } </b> 명
							</td>
							<td class="date">
								-
							</td>
						</tr>
					</tbody>
					
				</table>
			</div>
		</div>
	 </div>	
	
<%@include file="footer_pj.jsp" %>

	
</body>
</html>