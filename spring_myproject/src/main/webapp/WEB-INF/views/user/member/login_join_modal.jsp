<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <!-- 로그인 레이어 팝업 -->
	<div id="modal_login">
		<div id=layer_login>
			<p style="font-size:30px; text-align:center; margin-top:35px;">LOGIN</p><button id="close" onclick="login_layer_down()">X</button>
			<br>
				<div id="layer_login_body">
					<form name="login" method="post" action="/member/login" onsubmit="return login_form_chek()">
						<input id="login_email" type="text" name="email" placeholder="Email" required><br>
						<input id="login_pw" type="password" name="pw" maxlength="15" placeholder="PW">
						<p id="login_infor"></p>
						<input id="login_sub" type="submit" value="LOGIN"><br>
						<p id="go_join" onclick="join_layer_up()">회원가입</p>
						<p id="go_find" onclick="find_layer_up()">PW 찾기</p>
					</form>
				</div> 
		</div>
	</div>
	
<!-- 회원가입 레이어 팝업 -->
	<div id="modal_join"> 
		<div id="layer_join">
			<p style="font-size:30px; text-align:center; margin-top:35px;">JOIN</p><button id="close" onclick="join_layer_down()">X</button>
			<br>
				<div id="layer_join_body">
					<form id="join_form" name="join_member" method="post" action="/member/memRegProc" onsubmit="return join_member_check()">
					
						<input id="join_eid" type="text" name="e_id" placeholder="Email ID" required>@
						<input id="join_edomain" type="text" name="e_domain" placeholder="Domain" required>
						<input type="hidden" name="isEmailCheck" id="isEmailCheck">
						<select id="join_sel" name="e_domain_sel_what" onchange="e_domain_sel()">
							<option value="">직접입력</option>
							<option value="gmail.com">gmail.com</option>
							<option value="naver.com">naver.com</option>
							<option value="daum.net">daum.net</option>
						</select>
						<p id="email_infor"></p>
						<button onclick = "email_check()">중복확인</button>

						<input id="join_pw" type="password" name="pw" maxlength="15" placeholder="PW" required>
						<p id="name_pw_infor"></p>
						
						<input id="join_sub" type="submit" value="JOIN">
				</form>
				</div>
		</div>
	</div>
	
	
	
<!-- 회원 정보 찾기 레이어 팝업 -->
	<div id="modal_find"> 
		<div id="layer_find">
			<p style="font-size:30px; text-align:center; margin-top:35px;">FIND</p><button id="close" onclick="find_layer_down()">X</button>
			<br>
				<div id="layer_find_body">
					<form id="find_form" name="find_member" method="post" action="/member/memFindProc" onsubmit="return find_member_check()">
					
						<input id="find_email" type="text" name="email" placeholder="Email" required><br>
						오탈자 없이 <b>등록된 이메일</b>을 입력하여주세요.
						
						<input id="find_sub" type="submit" value="FIND"><br>
					</form>
				</div>
		</div>
	</div>