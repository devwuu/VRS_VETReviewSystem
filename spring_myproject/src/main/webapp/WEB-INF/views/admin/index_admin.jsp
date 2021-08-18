<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css_file/admin_css.css">
<title>[관리자]동물 병원 후기 모음집</title>
</head>
<body>
<script>

	window.onload = function(){
		if(document.getElementById("loginStat").value == 1){
			alert('잘못된 정보입니다.');
		}
	}


</script>

<div id=admin_login>
	<p style="font-size:30px; text-align:center; margin-top:35px;">ADMIN LOGIN</p>
	<br>
		<div id="layer_login_body">
			<input type="hidden" value="${stat }" id="loginStat">
			<form name="login" method="post" action="/admin/adminLogin">
				<input id="login_email" type="text" name="email" placeholder="Email" required><br>
				<input id="login_pw" type="password" name="pw" maxlength="15" placeholder="PW" required>
				<input id="login_sub" type="submit" value="LOGIN"><br>
			</form>
		</div> 
</div>


</body>
</html>