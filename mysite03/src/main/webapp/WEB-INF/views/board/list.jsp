<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<c:forEach items='${list }' var='vo' varStatus='status'>
						<tr>
							<td>${(count-status.index)-page*10 }</td>
							<td style="text-align:left; padding-left:${20*vo.depth }px">
								<c:if test="${vo.depth > 0 }">
									<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
								</c:if>
								<c:choose>
									<c:when test="${vo.delete eq 'false' }">
										<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }&page=${page }">${vo.title }</a>
									</c:when>
									<c:otherwise>
										<p>삭제된 게시글입니다.</p>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:if test="${authUser.no eq vo.userNo && vo.delete eq 'false' }">
									<a href="${pageContext.request.contextPath }/board?a=delete&page=${page }&no=${vo.no }" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>

				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${page ne 0 }">
							<li><a href="${pageContext.request.contextPath }/board?a=list&page=${page-1}">◀</a></li>
						</c:if>
						<c:forEach var='i' begin='${startPage }' end='${endPage }'>
							<c:choose>
								<c:when test="${page eq i }">
									<li class="selected">${i+1 }</li>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${i+1 > (count/10)+(1-((count/10)%1))%1 }">
											<li>${i+1 }</li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageContext.request.contextPath }/board?a=list&page=${i}">${i+1 }</a></li>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${page+1 != (count/10)+(1-((count/10)%1))%1 }">
							<li><a href="${pageContext.request.contextPath }/board?a=list&page=${page+1}">▶</a></li>
						</c:if>
					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=write"
						id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>