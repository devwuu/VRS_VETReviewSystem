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
<link rel="stylesheet" href="/css_file/board_notice_css.css">
<script src="/js_file/main_home_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>동물병원 통합 아카이빙 시스템 : 공지사항</title>
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
			
			<p id="page_infor">＠ 공지사항</p>
			<div id="board_list">
				<table id="noticeList" cellspacing="0">
				 	<thead>
						<tr>
							<td class="num">
								순번
							</td>
							<td class="title">
								제목
							</td>
							<td class="date">
								작성일
							</td>
							<td class="writer">
								작성자
							</td>
							<td class="cnt">
								조회수
							</td>
						</tr>
					</thead>
					
			
					<c:set value="${fn:length(noticeList)+1 }" var="cnt"/>
					<c:forEach items="${noticeList }" var="n">
						<c:set value="${cnt-1 }" var="cnt"/>
						<tbody>
							<tr onclick="location.href='/board/notice_contView?noticeNo=${n.noticeNo}'">
								<td>
									${cnt }
								</td>
								<td>
									${n.title }
								</td>
								<td>
									${n.wdate }
								</td>
								<td>
									${n.gradeName }
								</td>
								<td>
									${n.count }
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