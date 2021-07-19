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

<style>
	
	a#mypage{
		color: #ffffff !important;
	}

</style>



<title>동물병원 통합 아카이빙 시스템 : 내 정보</title>
</head>

<!-- 온로드 로그인상태 체크 함수 -->
<body>
<script>

	window.onload = function(){
		if(document.getElementById("mod_stat").value == 2){
			alert('비밀번호가 일치하지 않습니다.');
		}else if(document.getElementById("mod_stat").value == 1){
			alert('회원정보가 변경되었습니다.');
		}		
	}

</script>	
<!-- 로그인 및 회원가입창 -->
<%@include file="login_join_modal.jsp" %>				


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
		<c:set value="${member }"  var="m"/>
		<div id="main_right_column">
			<input type="hidden" id="mod_stat" value="${ mod_stat}">
			<form name="member_mody">
				<p id="email_view"><b>email</b> : ${m.email }</p>
				<p id="pw_view"><b>pw</b> : <input type="password" name="pw" required maxlength="15"></p>
				<p id="pwUpdate_view"><b>update pw</b> : <input type="password" name="pwUpdate" maxlength="15"></p>
				<p id="nick_view"><b>nickname</b> : <input type="text" value="${m.nickName }"
							maxlength="5" name="nickName" required></p>
				<p id="interest_view"><b>interest</b> : 
				<c:forEach items="${codeList }" var="c">
						<input type="checkbox" name="interest" value="${c.codeValue }"
						<c:forEach items="${m.interestCode }" var="inter">
							<c:if test="${ c.codeValue == inter.codeValue}">
									checked 
						   </c:if>
					    </c:forEach>
					    >${c.codeName }					    
				</c:forEach>
				
							
				</p>
				<input type="hidden" name="email" value="${m.email }">		
				<input id="modi_mem" type="submit" value="수정"
				onclick="javascript: form.action='/member/myInforMod'">
				<input id="del_mem" type="submit" value="탈퇴"
				onclick="javascript: form.action='/member/memDelRequest'">
			</form>
		</div>
		
	 </div>	
	
<%@include file="../footer_pj.jsp" %>

	
</body>
</html>