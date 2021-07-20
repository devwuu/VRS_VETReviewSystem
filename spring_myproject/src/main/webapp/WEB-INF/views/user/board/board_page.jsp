<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>        

<div id="board_page">

		<c:if test="${page.lastPage==0 }">
			<c:set value="1" var="cnt"/>				
		</c:if>
		
		<c:if test="${page.lastPage>0 }">
			<c:set value="${page.lastPage }" var="cnt"/>				
		</c:if>
	
			
<!-- 	이전/다음페이지 요청시 해당 페이지로 리스트가 전환되며 하단의 paging 갱신 -->
	
		<c:if test="${page.prev}">
			<a onclick="pagingSubmit('${page.startPage-1 }', '${condition }')">◀</a>
		</c:if>
		
		<c:forEach varStatus="status" begin="${page.startPage-1 }" end="${cnt-1 }" var="paging">
			<a id="page${paging+1 }" onclick="pagingSubmit('${paging+1 }','${condition }')">${paging+1 }</a>
			<c:set value="${cnt+1 }" var="cnt"/>
		</c:forEach>
		
		<c:if test="${page.next}">
			<a onclick="pagingSubmit('${page.lastPage+1 }', '${condition }')">▶</a>
		</c:if>
		

			
		

</div>
