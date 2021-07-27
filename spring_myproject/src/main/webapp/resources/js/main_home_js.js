
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


//회원 정보 찾기 모달창

function find_layer_up(){
	document.getElementById("modal_login").style.display="none";
	document.getElementById("modal_find").style.display="block";
}

function find_layer_down(){
	document.getElementById("modal_find").style.display="none";
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




//회원 정보 찾기 이메일 유효성 검사
function find_member_check(){
	
	
	var email = document.forms["find_member"]["email"].value;
	
	var x = new XMLHttpRequest();
	var rs = true;
	
	x.onreadystatechange = function(){
		
		if(x.readyState === 4){
			if(x.status === 200){
				
				if(x.responseText.trim() === "0"){
					
					rs = false;
					alert("등록되지 않은 회원 정보입니다.");
				}else{
					alert("이메일로 임시 비밀번호를 발송하였습니다.");				
				}
			}
		}
	};
	
	x.open("POST", "/member/email_check", false);
	//처리 순서를 명확하게 하기 위헤 async 옵션을 false로 설정(동기)
	
	x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	x.send("email_user="+email);
	
	//if문 안에서 return이 작동되지 않아 rs 플래그를 별도로 설정
	return rs;
	
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
	
	var url = encodeURIComponent(window.location.href);
	//특수문자가 포함되어있어 url의 전체를 인코딩 해준다
	
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
	
	
	x.send("seqno_r="+seqno_r+"&url_r="+url);
	
}



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


//snsRegForm 노출 및 수정 data 전송
//form이 여러개이기 때문에 form에 idx를 부여하여 idx로 접근
function getSnsModForm(idx){
	
	
	//수정form을 숨기면서 data전송
	if(document.forms[idx].snsContentModBox.style.display == "block"){
		
		
		//data전송 전 file 확장자 체크
		var filename = document.forms[idx].attachFileMod.value;
		var check = filename.substring(filename.lastIndexOf('.')+1);
		
		if(filename != "" &&!(check == "jpg" || check == "gif" || check == "jpeg" || check == "png")){
			alert('이미지파일(jpg, gif, jpeg, png)파일만 첨부 가능합니다.');
			return false;
		}
		
		
		if(document.forms[idx].snsContent.value.length == 0){
			alert('게시글 내용을 입력하세요');
			document.forms[idx].snsContent.focus();
			return false;
		}
		
	
		document.getElementById("snsContentView"+idx).style.display = "block";
		document.forms[idx].snsContentModBox.style.display = "none";
		
		if(document.forms[idx].delButton != undefined){
			document.forms[idx].delButton.type = "hidden";		
		}
		
		
		//ajax를 통한 data전송
		var x = new XMLHttpRequest();
		
		x.onreadystatechange = function(){
			
			if(x.readyState === 4){
				
				if(x.status === 200){
					
					if(x.responseText.trim() == "1"){
						alert("수정완료");
						
					
						//수정 완료 후 페이지 갱신
						location.reload(true);

						
					}else{
						alert("수정실패");
					}
					
				}else{
					console.log("에러: "+x.status);
				}
				
			}
			
		};
		
		//form이 multipart기 때문에 false로 지정해준다. 
		x.open("POST", "/board/snsUpdate", false);
		
		//form안의 data를 formdata객체에 담아준다.
		var formdata = new FormData(document.getElementById("updateSns"+idx));

		//formdata를 controller로 전달한다.
		x.send(formdata);
			
	}
		
		
	//수정form을 노출함
	if(document.forms[idx].snsContentModBox.style.display == "none"){		
	
		document.getElementById("snsContentView"+idx).style.display = "none";
		document.forms[idx].snsContentModBox.style.display = "block";
		
		if(document.forms[idx].delButton != undefined){
			document.forms[idx].delButton.type = "button";
		}else{
			document.forms[idx].attachFileMod.type = "file";
		}
		
	}
	
}



//sns 첨부파일 삭제
function snsFileDelReq(snsReviewNo, idx){
	
	var x = new XMLHttpRequest();
	
	x.onreadystatechange = function(){
		
		if(x.readyState === 4){
			if(x.status === 200){
				
				if(x.responseText.trim() == "1"){
					alert("삭제 완료");
					document.getElementById("existFile"+idx).style.display = "none";
					document.forms[idx].attachFileMod.type = "file";
				}else{
					alert("삭제 실패");
				}
				
			}else{
				console.log("에러: "+x.status);
			}
		}
		
	};
	
	
	x.open("POST", "/file/snsFileDel", true);
	x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	x.send("snsReviewNo="+snsReviewNo);
	
	
}


//게시글 페이지 이동
function pagingSubmit(pageNum, condition){
	

	if(condition == ""){
		//검색 조건 X
		
		document.forms['pagingForm']['pageNum'].value = pageNum;
		document.forms['pagingForm'].submit();
		
	}else{
		//검색 조건 O
		
		document.forms['searchForm']['pageNum'].value = pageNum;
		document.forms['searchForm'].submit();
	}
	
}


//덧글 등록
function replyRegProc(){
	
	var url = window.location.href;
	var x = new XMLHttpRequest();
	
	x.onreadystatechange  = function(){
		if(x.readyState === 4){
			if(x.status === 200){
				
				if(x.responseText.trim() == "1"){
					
					alert('등록 완료');
					location.href=url;
					
					//페이지에 표시되어야 하는 정보들이 있어서 기존의 url로 redirect
					
				}else{
					alert('등록 실패');
				}
				
			}
		}
	};
	
	x.open("POST", "/board/replyRegProc", true);
	
	var formdata = new FormData(document.getElementById("replyReg"));
	
	//fromdata 전송이기 때문에 content-type를 설정하지 않음
	
	x.send(formdata);

}


//덧글삭제
function replyDelProc(repNo){
	
	var result = confirm("삭제하시겠습니까?");
	
	if(result){
		
		var url = window.location.href;
		var x = new XMLHttpRequest();
		
		x.onreadystatechange = function(){
		
			if(x.readyState === 4){
				if(x.status === 200){
					
					if(x.responseText.trim() == "1"){
						alert("삭제 완료");
						location.href=url;
						
					}else{
						alert("삭제 실패");
					}
				}
			}
		};
	
	x.open("POST", "/board/replyDelProc", true);
	
	x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	x.send("replyNo="+repNo);
	
	}
	
}

//덧글 수정 form 활성화
function replyModFormShow(content){
	
	document.getElementById('replyListContent').style.display ="none";
	document.getElementById('replyModFormShowButton').style.display ="none";
	document.getElementById('replyModFormSubmit').style.display ="block";
	
	document.forms['replyModForm']['content'].style.display = "block";
	document.forms['replyModForm']['content'].value = content;
}


		

