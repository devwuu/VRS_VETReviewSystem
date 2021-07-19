

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
function fileDelReq(noticeNo){
	
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
	x.open("POST", "/admin/fileDel", true);
	x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	x.send("noticeNo="+noticeNo);
}

//전체 선택(체크박스 선택시 전체 선택됨)__탈퇴회원리스트
function selectAll(selectAll){
	
	const checkboxes = document.querySelectorAll('input[type="checkbox"]');
	
	checkboxes.forEach((checkbox) =>{
		checkbox.checked = selectAll.checked
	})
	
}

//체크 박스 선택 해제시 전체 선택 체크 박스 해제__탈퇴회원리스트
function selectCheck(checkbox){
	
	const checkAll = document.querySelector('input[name="selectAllInput"]');
	
	if(checkbox.checked === false){
		checkAll.checked = false;
	}
	
}

//전체 선택(체크박스 선택시 전체 선택됨)__병원리스트
function selectAllhos(selectAll){
	
	const checkboxes = document.querySelectorAll('input[name="checkBox"]');
	
	checkboxes.forEach((checkbox)=>{
		checkbox.checked = selectAll.checked
	})
	
}

//체크 박스 선택 해제시 전체 선택 체크 박스 해제__병원리스트
function selectCheckHos(checkbox){
	const checkAll = document.querySelector('input[name="allCheckBox"]');
	if(checkbox.checked==false){
		checkAll.checked = false;
	}
}


//병원 등록 modal open
function hospitalRegForm(){
	
	if(document.getElementById("hosRegModal").style.display == "block"){
		document.getElementById("hosRegModal").style.display = "none";
	}else{
		
		document.getElementById("hosRegModal").style.display = "block";
	}
	
}

//병원 등록 modal close
function hosRegFormClose(){
	document.getElementById("hosRegModal").style.display = "none";
}


//병원 수정 modal open
//기존에 등록되어 있는 정보를 매개변수로 가져옴
//code의 경우 몇개가 올 지 모르기 때문에 가변 변수로 설정
function updateModal(name, tel, post, add1, add2, add3, no, totalLen, ...theArgs){


	if(document.getElementById("hosModModal").style.display == "block"){
		document.getElementById("hosModModal").style.display = "none";
		for(var j=0; j<totalLen; j++){			
			document.getElementById('tagval'+j).checked = false;
		}
		
	}else{
		//병원 수정 modal open
		//가져온 매개변수를 input value에 할당함
		
		document.getElementById("hosModModal").style.display = "block";
		
		document.getElementById("hosNo").value = no;
		document.getElementById("postcodeMod").value = post;
		document.getElementById("addressMod").value = add1;
		document.getElementById("detailAddressMod").value = add2;
		document.getElementById("extraAddressMod").value = add3;
		document.getElementById("hospitalName").value = name;
		document.getElementById("hospitalTel").value = tel;
		
		
		//codevalue와 매개변수(codevalue)가 일치하면 체크박스에 checked
		for(var i = 0; i<theArgs.length; i++){
			for(var j=0; j<totalLen; j++){			
				if(theArgs[i] == document.getElementById('tagval'+j).value){
					document.getElementById('tagval'+j).checked = true;
				}
			}
		}
		
		
	}
	
}



//수정form 닫기
function hosModFormClose(totalLen){
	document.getElementById("hosModModal").style.display = "none";
	
	// tag value의 체크박스의 체크 해제
	for(var j=0; j<totalLen; j++){			
		document.getElementById('tagval'+j).checked = false;
	}
}


//병원 삭제 여부 확인
function checkYN(){
	return confirm("삭제하시겠습니까?");
}

