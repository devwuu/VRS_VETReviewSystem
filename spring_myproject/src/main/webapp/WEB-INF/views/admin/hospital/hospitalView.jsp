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
<link rel="stylesheet" href="/css_file/hospital_css.css">
<script src="/js_file/admin_js.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<style>
	
	a#hospitalView{
		color: #ffffff !important;
	}

</style>

<script>


	window.onload=function(){
		
		if(document.getElementById("regResult").value == "1"){
			alert("병원이 등록되었습니다.");
		}
		
	}

</script>


<title>동물병원 통합 아카이빙 시스템 : 회원정보</title>
</head>

<body>

<!-- 병원 등록 모달 -->
<%@include file="hosRegModal.jsp" %>
<input type="hidden" id="regResult" value=${rs }>

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
		
<!-- 검색 박스 -->
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
			
			
<!-- 		병원리스트				 -->
			<p id="page_infor">＠ 등록 병원 정보</p>
			<button id="hosRegButton" onclick="hospitalRegForm()">등록</button>
			<div id="hos_list">
				<table cellspacing="0">
				 	<thead>
						<tr>
							<td class="checkBox">
								<input type="checkbox" name="allCheckBox">
							</td>
							<td class="num">
								<b>순번</b>
							</td>
							<td class="hosName">
								<b>병원이름</b>
							</td>
							<td class="hosTel">
								<b>대표연락처</b>
							</td>
							<td class="hosAdd">
								<b>주소</b>
							</td>
							<td class="hosTag">
								<b>병원태그</b>
							</td>
						</tr>
					</thead>
					
					<c:set value="0" var="cnt"/>
					<c:forEach items="${hospitalList }" var="h" varStatus="status">
						<c:set value="${cnt+1 }" var="cnt"/>
						
						<tbody>
							<tr onclick="updateModal('${h.hospitalName }', '${h.hospitalTel }', '${h.post }', '${h.hospitalAdd1 }', 
													 '${h.hospitalAdd2 }', '${h.hospitalAdd3 }', '${h.hospitalNo }','${fn:length(codeList) }',
														 <c:forEach items="${h.code }" var="c" varStatus="status">
															'${c.codeValue }'<c:if test="${not status.last }">, </c:if>  									
														</c:forEach>
													 )">
								<td>
									<input type="checkbox" name="checkBox">
								</td>
								<td>
									${cnt }
								</td>
								<td>
									${h.hospitalName }
								</td>
								<td>
									${h.hospitalTel }
								</td>
								<td>
									(${h.post }) ${h.hospitalAdd1 }, ${h.hospitalAdd2 } ${h.hospitalAdd3 }
								</td>
								<td>
									<c:forEach items="${h.code }" var="c" varStatus="status">
										${c.codeName }<c:if test="${not status.last }"> <b>/</b> </c:if>  									
									</c:forEach>
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