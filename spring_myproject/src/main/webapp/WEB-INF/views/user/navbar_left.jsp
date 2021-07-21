<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--메인 왼쪽 컬럼 -->
	<div id="main_left_column">
		<c:if test="${sess_id != null }">
			<a id="logout_menu" href="/member/logout">로그아웃</a>
			<a id="mypage" href="/member/myInfor">내 정보</a>
			<a id="bookmarkList" href="/board/bookmarkList">북마크</a>	
		</c:if>
		<c:if test="${sess_id == null }">
	    <a id="login_menu" onclick="login_layer_up()">로그인</a>
	    </c:if>
		<a id="area_menu" class="area_menu_class" onclick="area_drop_down()">지역별 리뷰</a>
		<div id="area_drop" class="area_drop_class">

			<a id="boardReview" href="/board/hospitalList?location=서울">서울시</a>
			<a id="boardReview" href="/board/hospitalList?location=경기">경기도</a>
			<a id="boardSns" href="/board/hospitalListSns?location=부산">부산</a>

			<a href="#">전라도</a>			
		</div>
		<a href="#">예시메뉴1</a>
	</div> 