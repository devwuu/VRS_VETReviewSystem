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

<body>
<script>

	window.onload=function(){
		
		if(document.getElementById("delStat").value == 1){
			alert('게시글이 삭제되었습니다.');
		}
		
	}

</script>

<input type="hidden" value="${delStat }" id="delStat">

	
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
				<form name="searchForm" action="/board/reviewSearch" onsubmit="return selectCeck()">
					<select name="search" required>
						
						<option value="none"
						<c:if test="${select eq 'none' }"> selected</c:if>
						>분류</option>
						<option value="email"
						<c:if test="${select eq 'email' }"> selected</c:if>
						>작성자</option>
						<option value="title"
						<c:if test="${select eq 'title' }"> selected</c:if>
						>제목</option>
						<option value="content"
						<c:if test="${select eq 'content' }"> selected</c:if>
						>내용</option>
					</select>
					<input id="board_search" name="condition" type="text" placeholder="내용을 입력하세요" required 
					value="${condition }"
					maxlength="50">
					<input id="board_search_confirm" type="submit" value="확인">
				</form>
			</div>
						
			<div id="hospital1_view">
				<i id="bookmark_hos" class="material-icons">bookmark</i>
				<p id="hosName" ><b>OO 동물 병원</b></p>
				<p><b>주소</b> : XXXXX</p>
				<p><b>연락처</b> : XXXX</p>
				<p><b>24시, 토끼 전문</b></p>
			</div>
			<div id="board_list">
			
				<table cellspacing="0">
				 	<thead>
						<tr>
							<td class="num">
								<b>순번</b>
							</td>
							<td class="title">
								<b>제목</b>
							</td>
							<td class="date">
								<b>작성일</b>
							</td>
							<td class="writer">
								<b>작성자</b>
							</td>
							<td class="cnt">
								<b>조회수</b>
							</td>
						</tr>
					</thead>
					
					<c:set value="${fn:length(reviewList)+1 }" var="cnt"/>
					<c:forEach items ="${ reviewList}" var="list">
						<c:set value="${cnt-1 }" var="cnt"/>
						<tbody>
							<tr onclick="location.href='/board/board_contview?seqno_r=${list.reviewNo }'">
								<td>
									${cnt }
								</td>
								<td>
									${list.title }
								</td>
								<td>
									${list.wdate }
								</td>
								<td>
									${list.writer }
								</td>
								<td>
									${list.count }
								</td>
							</tr>
						</tbody>	
					</c:forEach>	
					
				</table>
			</div>
			<c:if test="${sess_id != null }">		
				<button onclick="location.href='/board/boardRegForm'">작성</button>
			</c:if>
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