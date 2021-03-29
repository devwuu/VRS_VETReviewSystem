<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>        

<div id="board_page">
	<c:if test="${page.prev}">
		<a>◀</a>
	</c:if>
	
	<c:forEach varStatus="status" begin="${page.startPage-1 }" end="${page.lastPage-1 }" var="paging">
		<a id="page${paging+1 }" onclick="location.href='/board/board_seoul?pageNum=${paging+1 }'">${paging+1 }</a>
	</c:forEach>
	<c:if test="${page.next}">
		<a onclick="nextPage(${page.lastPage}, ${page.maxPage })">▶</a>
	</c:if>
</div>
