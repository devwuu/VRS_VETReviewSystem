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


<title>동물병원 통합 아카이빙 시스템 : 회원정보</title>
</head>

<body>

<!-- 		병원 등록 modal -->
<div id="hosRegModal">
	<div id="hosRegFormDiv">
		<p id="modalInfor">Hospital Reg</p>
		<form id="hosRegForm" name="hosRegForm">
			 <p>병원 이름: <input type="text" name="hospitalName"> </p>
			 
			 <p>병원 대표 연락처: <input type="text" name="hospitalTel" placeholder="00-0000-0000 형식" maxlength="11"> </p>
			 <br>
			 
			 <p>병원 태그: <br>
				<input class="hostagcl" type="checkbox" name="hostag"> 24시
				<input class="hostagcl" type="checkbox" name="hostag"> 토끼
			 </p>
			 <br>
			 
			 <p>병원 주소:</p>
			 	<div id="addressDiv">
				 	<input type="text" id="postcode" placeholder="우편번호">
					<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
					<input type="text" id="address" placeholder="주소">
					<input type="text" id="detailAddress" placeholder="상세주소"><br>
					<input type="text" id="extraAddress" placeholder="참고항목">
					
				</div>	 
				<div id="wrap" style="display:none; border:1px solid; width:500px; height:300px; margin:5px 0; position:relative">
					<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
				</div>
							 	
				<script>
					// 다음 우편주소 api
				    // 우편번호 찾기 찾기 화면을 넣을 element
				    var element_wrap = document.getElementById('wrap');
				
				    function foldDaumPostcode() {
				        // iframe을 넣은 element를 안보이게 한다.
				        element_wrap.style.display = 'none';
				        document.forms['hosRegForm']['hosRegOk'].style.display='block';
				    }
				
				    function execDaumPostcode() {
				        // 현재 scroll 위치를 저장해놓는다.
				        var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
				        new daum.Postcode({
				            oncomplete: function(data) {
				                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
				
				                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
				                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				                var addr = ''; // 주소 변수
				                var extraAddr = ''; // 참고항목 변수
				
				                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				                    addr = data.roadAddress;
				                } else { // 사용자가 지번 주소를 선택했을 경우(J)
				                    addr = data.jibunAddress;
				                }
				
				                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				                if(data.userSelectedType === 'R'){
				                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
				                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
				                        extraAddr += data.bname;
				                    }
				                    // 건물명이 있고, 공동주택일 경우 추가한다.
				                    if(data.buildingName !== '' && data.apartment === 'Y'){
				                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				                    }
				                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				                    if(extraAddr !== ''){
				                        extraAddr = ' (' + extraAddr + ')';
				                    }
				                    // 조합된 참고항목을 해당 필드에 넣는다.
				                    document.getElementById("extraAddress").value = extraAddr;
				                
				                } else {
				                    document.getElementById("extraAddress").value = '';
				                }
				
				                // 우편번호와 주소 정보를 해당 필드에 넣는다.
				                document.getElementById('postcode').value = data.zonecode;
				                document.getElementById("address").value = addr;
				                // 커서를 상세주소 필드로 이동한다.
				                document.getElementById("detailAddress").focus();
				
				                // iframe을 넣은 element를 안보이게 한다.
				                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
				                element_wrap.style.display = 'none';
				                document.forms['hosRegForm']['hosRegOk'].style.display='block';
				
				                // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
				                document.body.scrollTop = currentScroll;
				            },
				            // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
				            onresize : function(size) {
				                element_wrap.style.height = size.height+'px';
				            },
				            width : '100%',
				            height : '100%'
				        }).embed(element_wrap);
				
				        // iframe을 넣은 element를 보이게 한다.
				        element_wrap.style.display = 'block';
				        document.forms['hosRegForm']['hosRegOk'].style.display='none';
				    }
				</script>
			 	
			
			 <input id="hosRegOk" name='hosRegOk' type="submit" value="등록">					 
		</form>
	</div>
</div>


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
					<c:forEach items="${hospitalList }" var="h">
						<c:set value="${cnt+1 }" var="cnt"/>
						<tbody>
							<tr>
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
									${h.hospitalAdd }
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