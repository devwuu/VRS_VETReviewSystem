<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--메인 왼쪽 컬럼 -->
	<div id="main_left_column">
		<a id="logout_menu" href="/admin/logout">로그아웃</a>
		<a id="noticeList" href="/admin/noticeList">공지사항</a>
		<a id="memList" href="/admin/memList">회원관리</a>
		<a id="delMemList" href="/admin/delMemList">탈퇴회원관리</a>
		<a id="area_menu" class="area_menu_class" onclick="area_drop_down()">지역별 리뷰</a>
		<div id="area_drop" class="area_drop_class">
			<a href="#">서울시</a>
			<a href="#">경기도</a>
			<a href="#">충청도</a>
			<a href="#">전라도</a>			
		</div>
		<a href="#">예시메뉴1</a>
	</div> 