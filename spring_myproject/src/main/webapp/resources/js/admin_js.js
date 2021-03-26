

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
