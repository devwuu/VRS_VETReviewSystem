
//로그인 모달창
function login_layer_up(){
	document.getElementById("modal_login").style.display="block";
}

function login_layer_down(){
	document.getElementById("modal_login").style.display="none";
}


//회원가입 모달창
function join_layer_up(){
	document.getElementById("modal_login").style.display="none";
	document.getElementById("modal_join").style.display="block";
}

function join_layer_down(){
	document.getElementById("modal_join").style.display="none";
	document.getElementById("modal_login").style.display="block";
}

//이메일 도메인 자동입력
function e_domain_sel(){
	var e_domain_chan = document.forms["join_member"]["e_domain_sel_what"].value;
	document.forms["join_member"]["e_domain"].value = e_domain_chan;	
	
	//이메일 도메인 수정 불가설정
	if(e_domain_chan!=""){
		document.forms["join_member"]["e_domain"].readOnly=true;
	}else{
		document.forms["join_member"]["e_domain"].readOnly=false;
	 }
}


//로그인 폼체크
function login_form_chek(){

	var pw_length = document.forms["login"]["pw"].value.length;
	
	if(pw_length<5){
		document.getElementById("login_infor").innerHTML="PW는 <b><u>최소 5자 이상</b></u> 입력해주세요.";
		return false;
	}
}

//회원가입 폼체크
function join_member_check(){
	
	var sub_pw = document.forms["join_member"]["pw"].value;
	
	if(sub_pw.length<5){
		document.getElementById("name_pw_infor").innerHTML="PW는 <b><u>5자 이상</b></u> 입력해주세요.";
		return false;
	}
	
	if(document.getElementById("isEmailCheck").value != "0"){
		document.forms["join_form"]["e_id"].focus();
		return false;
	}
	
}

//email 중복검사

function email_check(){
	var email_user_input = document.forms["join_member"]["e_id"].value+"@"+document.forms["join_member"]["e_domain"].value;
	var x = new XMLHttpRequest();
	
	x.onreadystatechange = function(){
		if(x.readyState === 4){
			if(x.status === 200){
				document.getElementById("isEmailCheck").value = x.responseText.trim();
				if(x.responseText.trim()==="0"){
					document.getElementById("email_infor").innerHTML="사용 가능한 Email입니다.";
				}else{
					document.getElementById("email_infor").innerHTML="중복된 Email입니다.";
				}
			}
		}
	};
	x.open("POST", "/member/email_check", true);
	x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	x.send("email_user=" + email_user_input);
//  onchange가 안먹어서 버튼 따로 만들었다네
}	


//미니 메뉴 dropdown(초기값을 불러오지 못하기 때문에 block으로 불러올 때만 none이게 해야함)
function mini_menu_drop(){
	if(document.getElementById("mini_menu_list").style.display=="block"){
		document.getElementById("mini_menu_list").style.display="none";
		document.getElementById("welcom").style.display="none";
	}else{
		document.getElementById("mini_menu_list").style.display="block";
		document.getElementById("welcom").style.display="block";
	}
}
	
//area 메뉴 펼치기
function area_drop_down(){
	if(document.getElementById("area_drop").style.display=="block"){
		document.getElementById("area_drop").style.display="none";
	}else{
		document.getElementById("area_drop").style.display="block"
	}
}

function mini_area_drop_down(){
	if(document.getElementById("mini_area_drop").style.display=="block"){
		document.getElementById("mini_area_drop").style.display="none";
	}else{
		document.getElementById("mini_area_drop").style.display="block"
	}
}
	

//첨부파일 삭제
function fileDelReq(seqno_r){
	
	var x = new XMLHttpRequest();
	x.onreadystatechange = function(){
		if(x.readyState === 4){
			if(x.status === 200){
				if(x.responseText.trim() == "1"){
					alert('삭제완료');
					document.getElementById("file_area").innerHTML="<input type='file' name='fileAttach'>";
				}else{
					alert('삭제실패');
				}
			}else{
				console.log('에러코드: '+x.status);
			}
		}
	};
	x.open("POST", "/file/fileDel", true);
	x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	x.send("seqno_r="+seqno_r);
}


//북마크 기능

function bookmark(seqno_r){
	
	var url = window.location.href;
	var x = new XMLHttpRequest();
	x.onreadystatechange = function(){
		if(x.readyState === 4){
			if(x.status === 200){
				if(x.responseText.trim() == "1"){
					document.getElementById("bookmark").innerHTML = "bookmark";
					alert('북마크 등록');
				}else if(x.responseText.trim() == "2"){
					document.getElementById("bookmark").innerHTML = "bookmark_border";
					alert('북마크 해제');
				 }
			}else{
				console.log('x.status: '+x.status);
			 }
		}
	};
	
	
	x.open("POST", "/board/bookmarkProc", true);
	x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	x.send("seqno_r="+seqno_r + "&url_r="+url);
	
}

//회원정보 수정시 비밀번호 체크
//function pwCheck(){
//
//	//보안상 문제가 없는지 검토가 필요한 부분.
//	//db에서 가져온 pw정보가 input에 hidden으로 들어가있어서 보안상 문제가 될 수도 있을 것 같음
//	//보안상의 문제로 우선 주석 처리
//	
//	if(document.forms["member_mody"]["pw_db"].value == document.forms["member_mody"]["pw_input"].value){
//		return ture;
//	}else{
//		alert('비밀번호가 일치하지 않습니다.');
//		return false;
//	}
//	
//}


//검색 form 체크
function selectCeck(){
	
	if(document.forms["searchForm"]["search"].value == "none"){
		alert('분류를 선택하세요');
		return false;
	}else{
		return true;
	}
}


//첨부파일 확장자 체크
function fileCheck(){
	
	var filename = document.forms["snsRegForm"]["attachFile"].value;
	var check = filename.substring(filename.lastIndexOf('.')+1);
	
	
	if(filename != "" &&!(check == "jpg" || check == "gif" || check == "jpeg" || check == "png")){
		alert('이미지파일(jpg, gif, jpeg, png)파일만 첨부 가능합니다.');
		return false;
	}

	return true;	
}


//snsRegForm 노출
//form이 여러개이기 때문에 idx로 접근
function getSnsModForm(idx){
	
	if(document.forms[idx].snsContentModBox.style.display == "block"){
	
		document.getElementById("snsContentView"+idx).style.display = "block";
		document.forms[idx].snsContentModBox.style.display = "none";
		
		if(document.forms[idx].fileName != undefined){
			document.forms[idx].fileName.type = "hidden";		
		}
		
		document.forms[idx].submit();
		
	}else if(document.forms[idx].snsContentModBox.style.display == "none"){		
	
		document.getElementById("snsContentView"+idx).style.display = "none";
		document.forms[idx].snsContentModBox.style.display = "block";
		
		if(document.forms[idx].fileName != undefined){
			document.forms[idx].fileName.type = "button";
		}
		
	}
	
}



//sns 첨부파일 삭제
function snsFileDelReq(snsReviewNo){
	alert(snsReviewNo);
}




