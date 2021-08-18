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
<link rel="stylesheet" href="/css_file/tag_css.css">
<link rel="stylesheet" href="/css_file/hospital_css.css">
<script src="/js_file/admin_js.js"></script>

<style>
	
	a#tagView{
		color: #ffffff !important;
	}

</style>

<script>

	window.onload = function(){
		if(document.getElementById("codeResult").value == "1"){
			alert("태그 등록이 완료되었습니다");
		}else if(document.getElementById("codeResult").value == "2"){
			alert("태그 삭제가 완료되었습니다");
		}
	}

</script>



<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>[관리자]동물 병원 후기 모음집 : 태그</title>
</head>

<body>

<!-- 태그 등록 모달 -->
<%@include file="hosRegModal.jsp"%>	

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
				<form action="/admin/delMemberSearch">
					<input id="board_search" type="text" name="condition" required
					maxlength="8">
					<input id="board_search_confirm" type="submit" value="검색">
				</form>
			</div>
						
			<p id="page_infor">＠ 등록 태그 리스트</p>
		 	<input id="codeResult" name="codeResult" type="hidden" value="${codeResult }">
		 	<button id="tegRegButton" onclick="tegRegFormPlz()">태그 등록</button>
		 	
			<form name="tagDelForm" method="POST" action="/admin/tag/tagDel">
				<div id="code_list">
					<table cellspacing="0">
					 	<thead>
							<tr>
								<td class="check">
									<input type="checkbox" name="selectAllInput" value="전체선택" onclick="selectAll(this)">
								</td>
								<td class="num">
									<b>순번</b>
								</td>
								<td class="codeValue">
									<b>코드번호</b>
								</td>
								<td class="codeName">
									<b>코드명</b>
								</td>
								<td class="wdate">
									<b>등록일</b>
								</td>
								
							</tr>
						</thead>
						
						<c:set var="cnt" value="${fn:length(tagList) }"/>
						<c:forEach items="${tagList }" var="t">
						
							<tbody>
								<tr>
									<td class="check">
									<input type="checkbox" name="codeValue" value="${t.codeValue }" onclick="selectCheck(this)">
									</td>
									<td>
										${cnt }
									</td>
									<td>
										${t.codeValue }
									</td>
									<td>
										${t.codeName }
									</td>
									<td>
										${t.wdate }
									</td>
									<c:set var="cnt" value="${cnt-1 }"/>
									
								</tr>
							</tbody>
						</c:forEach>	
					</table>
					<br>
					<input type="submit" value="태그 삭제" id="delbutton">
				</div>
			<form>
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