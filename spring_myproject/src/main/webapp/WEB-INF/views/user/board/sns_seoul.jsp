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
<link rel="stylesheet" href="/css_file/sns_seoul_css.css">
<script src="/js_file/main_home_js.js"></script>

<!-- 폰트 -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">

<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<style>
	
	a#boardSns{
		color: #ffffff !important;
	}
	
	div#area_drop{
		display: block !important;
	}

</style>



<title>동물병원 통합 아카이빙 시스템 : 서울 - XX 동물병원</title>
</head>

<body>
<script>

	window.onload=function(){
		
		if(document.getElementById("delStat").value == 1){
			alert('게시글이 삭제되었습니다.');
		}
		
	}
	
	
	function delConfirm(snsNo){
		
		if(confirm("삭제하시겠습니까")){
			location.href='/board/snsDel?snsNo='+snsNo;
		}else{
			return false;
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
						
			<div id="hospital1_view">
				<i id="bookmark_hos" class="material-icons">bookmark</i>
				<p id="hosName" ><b>OO 동물 병원</b></p>
				<p><b>주소</b> : XXXXX</p>
				<p><b>연락처</b> : XXXX</p>
				<p><b>응급실, 강아지 전문</b></p>
			</div>
			
			<div id="board_list">
			
				<form method="POST" name="snsRegForm" enctype="multipart/form-data" action="/board/snsRegProc" onsubmit="return fileCheck()">
					<c:if test="${sess_id != null }">		
						<table id="sns_regForm">
							<tr>
								<td id="snsText">
									<textarea name="snsContent" maxlength="1000" id="snsInput" required placeholder="내용을 입력하세요"></textarea>
								</td>
								<td id="snsButton"  rowspan="2" >
									<input id="snsSubmit" type="submit" value="등록">
								</td>
							</tr>
							<tr>
								<td>
									<input id="snsFile" type="file" name="attachFile" accept="image/gif, image/jpeg, image/png">
								</td>
							</tr>
						</table>	
					</c:if>
				</form>
				<div id=snsList>
					<table id="sns_list">
						<c:set value="3" var="idx"/>
						<c:forEach items="${snsList }" var="sns">
							<form name="updateSns" id="updateSns${idx }" enctype="multipart/form-data">
								<tbody>
									<tr>
										<td class="snsWriter">
											<b>${sns.email }</b>
											/ ${sns.wdate }
											
											<input type="hidden" name="email" value="${sns.email }">
											<input type="hidden" name="wdate" value="${sns.wdate }">
											<input type="hidden" name="snsReviewNo" value="${sns.snsReviewNo }">
											
										</td>
										<td class="snsContent">
											<p id="snsContentView${idx }">${sns.snsContent }</p>
											<textarea id="snsContentModBox" name="snsContent" style="display:none">${sns.snsContent }</textarea>
										
										</td>
										<td class="contentFile">
											<input type="hidden" name="attachFileMod" accept="image/gif, image/jpeg, image/png">
											<div id="existFile${idx }">
												<c:set value="${sns.fileAttached }" var="f"/>
												<c:if test="${f.fileName != null}">
													<c:if test="${fn:contains(f.fileType, 'image') }">
														<img onclick="window.open(this.src)" id="fileAttached" src="/upload/${f.fileNameSave }">
														<br>
														<input type="hidden" name="delButton" onclick="snsFileDelReq(${sns.snsReviewNo }, ${idx })" value="첨부파일삭제">
													</c:if>
		<!-- 											js 함수를 통해 확장자 체크 실시 -->
		<%-- 											<c:if test="${!fn:contains(f.fileType, 'image') }"> --%>
		<%-- 												<a onclick="window.open(this.href); return false;" href="/upload/${f.fileNameSave }">${f.fileName} </a> --%>
		<%-- 											</c:if> --%>
												</c:if>
											</div>
										</td>
										<td class="snsEdit">
											<c:if test="${sess_id eq sns.email }">
												<i class="fas fa-edit" onclick="getSnsModForm(${idx})"></i>
											</c:if>
										</td>
										<td class="snsDel">
											<c:if test="${sess_id eq sns.email }">										
												<i class="fas fa-trash-alt" onclick="delConfirm(${sns.snsReviewNo })"></i>
											</c:if>
										</td>
									</tr>
								</tbody>
							</form>
							
<!-- 							form에 idx를 매개변수로 이용하고 난 다음 증가시킴 -->
							<c:set value="${idx+1 }" var="idx"/>							
						</c:forEach>
						
					</table>
				</div>
				
			</div>
			
		</div>
			
	 </div>	


	
<%@include file="../footer_pj.jsp" %>

	

	
	
	
</body>
</html>