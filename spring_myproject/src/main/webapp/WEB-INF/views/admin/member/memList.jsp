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
<link rel="stylesheet" href="/css_file/mem_css.css">
<script src="/js_file/admin_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<style>
	
	a#memList{
		color: #ffffff !important;
	}

</style>



<title>동물병원 통합 아카이빙 시스템 : 회원정보</title>
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
		<div id="main_right_column_board">
		
			<div id="board_search_box">
				<form name="search" action="/admin/memberSearch">
					<select name="grade">
						<option value="0"
						<c:if test="${gradeCode == 0 }">selected</c:if>
						>회원 등급</option>
						
						<option value="1"						
						<c:if test="${gradeCode == 1 }">selected</c:if>
						>관리자</option>
						
						<option value="2"
						<c:if test="${gradeCode == 2 }">selected</c:if>
						>일반회원</option>
					</select>
					<input id="board_search" name="condition" type="text" 
							value="${condition }"
							placeholder="email을 입력하세요">
					<input id="board_search_confirm" type="submit" value="검색">
				</form>
			</div>
						
			<p id="page_infor">＠ 등록 회원 정보</p>
			<div id="mem_list">
				<table cellspacing="0">
				 	<thead>
						<tr>
							<td class="num">
								<b>순번</b>
							</td>
							<td class="email">
								<b>이메일</b>
							</td>
							<td class="name">
								<b>별명</b>
							</td>
							<td class="recom">
								<b>추천 수</b>
							</td>
							<td class="report">
								<b>신고 수</b>
							</td>
							<td class="grade">
								<b>회원 등급</b>
							</td>
							<td class="wdate">
								<b>가입일</b>
							</td>
							<td class="isdel">
								<b>탈퇴 요청 여부</b>
							</td>
						</tr>
					</thead>
					
					<c:set value="${fn:length(memberList)+1 }" var="cnt"/>
					<c:forEach items="${memberList }" var="m">
						<c:set value="${cnt-1 }" var="cnt"/>
						<tbody>
							<tr>
								<td>
									${cnt }
								</td>
								<td>
									${m.email }
								</td>
								<td>
									${m.nickName }
								</td>
								<td>
									${m.recomCount }
								</td>
								<td>
									${m.reportCount }
								</td>
								<td>
									${m.gradeName }
								</td>
								<td>
									${m.wdate }
								</td>
								<td>
									${m.isDel }
								</td>
							</tr>
						</tbody>	
					</c:forEach>
				</table>
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