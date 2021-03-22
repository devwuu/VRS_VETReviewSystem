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

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<style>
	
	a#noticeList{
		color: #ffffff !important;
	}

</style>


<title>동물병원 통합 아카이빙 시스템 : 공지사항</title>
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
						
			<p id="page_infor">＠ 공지사항</p>
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
					
					<c:set value="${fn:length(NoticeList)+1 }" var="cnt"/>
					<c:forEach items="${NoticeList }" var="list">
						<c:set value="${cnt-1 }" var="cnt" />
						<tbody>
							<tr onclick="location.href='/admin/noticeView?noticeNo=${list.noticeNo}'">
								<td>
									${cnt }
								</td>
								<td>
									${list.title}
								</td>
								<td>
									${list.wdate}
								</td>
								<td>
									${list.gradeName}
								</td>
								<td>
									${list.count}
								</td>
							</tr>
						</tbody>	
					</c:forEach>
					
				</table>
			</div>
			
			<c:if test="${sess_nickname != null}">	
			<button onclick="location.href='/admin/noticeWrite'">작성</button>
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