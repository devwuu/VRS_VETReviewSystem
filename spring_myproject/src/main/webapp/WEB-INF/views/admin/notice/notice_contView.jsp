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
<link rel="stylesheet" href="/css_file/contentView_css.css">
<script src="/js_file/admin_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<title>동물병원 통합 아카이빙 시스템 : 공지사항</title>
</head>

<!-- 온로드 로그인상태 체크 함수 -->
<body>


<script>
function checkYN(){
	if(confirm("삭제하시겠습니까?")){
		 return true;
	}else{
		return false;
	}
}
</script>

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
			<c:set value="${notice }" var="n" />
				<br>
				<p id="page_infor">＠ 공지사항</p>
				<p id="title_cont"><b>Title</b> : ${n.title }</p>
				<p id="email_cont"><b>Writer</b>: ${n.gradeName }</p>
				<p id="date_cont"><b>Wdate</b> : ${n.wdate }</p>
				<p id="date_cont"><b>Mdate</b> : ${n.mdate }</p>
				<div id = "content_view">
					${n.content }
				</div>
				<c:set value="${n.fileAttached }" var="f" />
				
				<c:if test="${ sess_id != null }">
					<c:if test="${sess_id eq n.writer }">
						<form method="POST" action="/admin/noticeModForm">
							<input type="hidden" value="${n.noticeNo }"  name="noticeNo">
							<input type="hidden" value="${n.title }"  name="title">
							<input type="hidden" value="${n.gradeName }"  name="gradeName">
							<input type="hidden" value="${n.wdate }"  name="wdate">
							<input type="hidden" value="${n.mdate }"  name="mdate">
							<input type="hidden" value="${n.content }"  name="content">
						
							<c:if test="${f.fileName != null }">
								<input type="hidden" value="${f.fileName }"  name="fileName">
								<input type="hidden" value="${f.fileNameSave }"  name="fileNameSave">
								<input type="hidden" value="${f.fileSize }"  name="fileSize">
								<input type="hidden" value="${f.fileType }"  name="fileType">
								<input type="hidden" value="${f.filePath }"  name="filePath">
							</c:if>
							<input id="modify" type="submit" value="수정">
						</form>
					
						<form method="POST" action="/admin/noticeDel" onsubmit="return checkYN()">					
								<input type="hidden" value="${n.noticeNo }"  name="noticeNo">
								<input id="del" type="submit" value="삭제">
						</form>
						
					</c:if>
				</c:if>
				<div id="file_view">
					<br>
					<c:if test="${f.fileName != null }">
						<a>파일이름: ${f.fileName } </a> (size: ${f.fileSize })<br>
						<c:if test="${fn:contains(f.fileType, 'image') }">
							<img src = "/upload/${f.fileNameSave }"
								style="width:auto; height:100px">
								
						</c:if>
						<br>
						<form method="POST" action="/admin/fileDown">					
						
							<input type="hidden" value="${f.fileNameSave }"  name="fileNameSave">
							<input type="hidden" value="${f.filePath }"  name="filePath">
							<input type="submit" value="다운로드">
						</form>
					</c:if>
				</div>
		</div>
		
	 </div>	
	
<%@include file="../footer_pj.jsp" %>

	
</body>
</html>