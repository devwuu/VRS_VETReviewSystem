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
<link rel="stylesheet" href="/css_file/bookmarkList_css.css">
<script src="/js_file/main_home_js.js"></script>

<style>
	
	a#bookmarkList{
		color: #ffffff !important;
	}

</style>


<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>동물병원 통합 아카이빙 시스템 : 북마크</title>
</head>

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
			<div id="book_list">
				<p id="book_infor">북마크 한 게시글</p>
				<table cellspacing="0">
				 	<thead>
						<tr>
							<td class="num">
								순번
							</td>
							<td class="title">
								제목
							</td>
							<td class="writer">
								작성자
							</td>
							<td class="date">
								작성일
							</td>
							<td class="cnt">
								조회수
							</td>
						</tr>
					</thead>

					<c:set value="${fn:length(bookmark)+1 }" var="cnt"/>
					<c:forEach items="${bookmark }" var="bm">
						<c:set value="${cnt-1 }" var="cnt"/>
						<tbody>
							<tr onclick="location.href='${bm.reviewUrl}'">
								<td class="num">
									${cnt }
								</td>
								<td class="title">
									${bm.title}
								</td>
								<td class="writer">
									${bm.writer}
								</td>
								<td class="date">
									${bm.wdate}
								</td>
								<td class="cnt">
									${bm.count}
								</td>
							</tr>
						</tbody>
					</c:forEach>
					
				</table>
			</div>
		</div>
	 </div>	
	
<%@include file="../footer_pj.jsp" %>

	
</body>
</html>