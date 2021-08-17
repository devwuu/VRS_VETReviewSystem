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
<script src="/js_file/main_home_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>동물 병원 후기 모음집</title>
</head>

<!-- 온로드 로그인상태 체크 함수 -->
<body>

<!-- 로그인 실패시 모달창 자동 발생 -->
<script>
window.onload = function(){
	
	
	if(document.getElementById("titleCheck") == null){
		location.href="/board/noticeListIndex";		
	}
	
	if(document.getElementById("logstat_meg").value == 1){
		alert('로그인 정보가 잘못되었습니다.');
// 		document.getElementById("modal_login").style.display="block";
	}else if(document.getElementById("logstat_meg").value == 2){
		alert('회원가입이 완료되었습니다.');
	}else if(document.getElementById("logstat_meg").value == 3){
		alert('임시 비밀번호 발송이 완료되었습니다.');
	}
}

</script>

<!-- 로그인 상태 회신 받는 창 -->
<input type="hidden" id="logstat_meg" value="<c:out value="${ login_stat}"/>">
	
<!-- 로그인 및 회원가입창 -->
<%@include file="member/login_join_modal.jsp" %>				


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
	
			


			<div id="notice_list">
				<p id="notice_infor"><a href="/board/noticeList">공지사항 ▶ </a></p>
				<table id="noticeListIndex" cellspacing="0">
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
							<td class="date">
								<b>작성자</b>
							</td>
							<td class="cnt">
								<b>조회수</b>
							</td>
						</tr>
					</thead>



					<c:set value="${fn:length(noticeList) +1 }" var="cnt"/>
					<c:forEach items="${noticeList }" var="n">
						<c:set value="${cnt-1 }" var="cnt"/>
						<tbody>
							
							<tr onclick="location.href='/board/notice_contView?noticeNo=${n.noticeNo}'">
								<td class="num">
									${cnt }
								</td>
								<td class="title" id="titleCheck">
									${n.title }
								</td>
								<td class="date">									
									${n.wdate }
								</td>
								<td class="date">									
									${n.gradeName }
								</td>
								<td class="cnt">
									${n.count }
									
								</td>
							</tr>
						</tbody>
					</c:forEach>				
				</table>
				
			</div>
		</div>
	 </div>	
	
<%@include file="footer_pj.jsp" %>

	
</body>
</html>