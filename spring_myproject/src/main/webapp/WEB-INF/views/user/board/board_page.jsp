<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>        

<div id="board_page">

		<c:if test="${page.prev}">
			<a>◀</a>
		</c:if>
		
			
<!-- 			검색X -->
		<c:if test="${condition == null}">
			<c:forEach varStatus="status" begin="${page.startPage-1 }" end="${page.lastPage-1 }" var="paging">
				<a id="page${paging+1 }" onclick="location.href='/board/board_seoul?pageNum=${paging+1 }'">${paging+1 }</a>				
			</c:forEach>
			<c:if test="${page.next}">
<!-- 				다음페이지 요청시 ajax를 통해 paging 갱신 / 현재 게시글 list는 변동 없음 -->
				<a onclick="nextPage(${page.lastPage}, ${page.maxPage })">▶</a>
			</c:if>
		</c:if>
			
<!-- 			검색O -->
		<c:if test="${condition != null}">
		
			<c:if test="${page.lastPage==0 }">
				<c:set value="1" var="cnt"/>				
			</c:if>
			
			<c:if test="${page.lastPage>0 }">
				<c:set value="${page.lastPage }" var="cnt"/>				
			</c:if>
			
			<c:forEach varStatus="status" begin="${page.startPage-1 }" end="${cnt-1 }" var="paging">
				<a id="page${paging+1 }" onclick="location.href='/board/reviewSearch?pageNum=${paging+1 }&search=${select }&condition=${condition }'">${paging+1 }</a>
				<c:set value="${cnt+1 }" var="cnt"/>
			</c:forEach>
			
<!-- 			다음페이지 요청시 해당 페이지로 리스트가 전환되며 하단의 paging 갱신 -->
			<c:if test="${page.next}">
				<a onclick="location.href='/board/reviewSearch?pageNum=${page.lastPage+1 }&search=${select }&condition=${condition }'">▶</a>
			</c:if>
			
		</c:if>
			
		

</div>
