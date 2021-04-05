<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 미니 메뉴 영역 -->
	<div id="mini_menu" onclick="mini_menu_drop()" class="mini_menu_class"><i class="fa fa-bars"></i></div>
	
	<div id="mini_menu_list">
		
		<a id="logout_menu" href="/admin/logout">로그아웃</a>
		<a id="noticeList" href="/admin/noticeList">공지사항</a>
		<a id="memList" href="/admin/memList">회원관리</a>
		<a id="delMemList" href="/admin/delMemList">탈퇴회원관리</a>
		<a id="hospitalView" href="/admin/hospital/hospitalView">병원관리</a>
		<a id="mini_menu_area_menu" class="mini_area_menu_class" onclick="mini_area_drop_down()">지역별 리뷰</a>
		<div id="mini_area_drop" class="mini_area_drop_class">
			<a href="#">서울시</a>
			<a href="#">경기도</a>
			<a href="#">충청도</a>
			<a href="#">전라도</a>			
		</div>
		<a href="#">예시메뉴1</a>
	</div>

	
	