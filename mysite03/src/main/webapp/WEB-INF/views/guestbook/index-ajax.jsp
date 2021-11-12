<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <!-- <script src="https://code.jquery.com/jquery-3.6.0.js"></script> -->
  <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
  
<script>

var listEJS = new EJS({
	url: '${pageContext.request.contextPath }/assets/ejs/list-guestbook.ejs'
});

var startNo;
var fetch = function() {
	var url = '${pageContext.request.contextPath }/guestbook/api/list' + (startNo ? ('?sn=' + startNo) : '');
	
	$.ajax({
		url: url,
		dataType: 'json',
		type: 'get',
		success: function(response) {
			console.log(response);
			
			var html = listEJS.render(response);			
			
				//html += "<li data-no='" + response.data.no + "'>"
				//	+ "<strong>" + response.data.name + "</strong>"
				//	+ "<p>" + response.data.message.replace(/\n/g, '<br/>') + "</p>"
				//	+ "<strong></strong>"
				//	+ "<a href='' data-no='" + response.data.no + "'>삭제</a>";
			
			console.log("html:" + html);
			$("#list-guestbook").append(html);
			
			if (response != "" && response != null) {
				startNo = Number($('#list-guestbook li').last().data('no')) + 1 || 0;
			}
		},
		error: function(xhr, message, error) {
			console.log(message + ":" + error);
		}
	});
}

var messageBox = function(title, message, callback) {
	$('#dialog-message').attr('title', title);
	$('#dialog-message p').text(message);
	$('#dialog-message').dialog({
		modal: true,
		buttons: {
			"확인": function() {
				$(this).dialog('close');
			}
		},
		close: callback
	});	
}

$(function(){
	fetch();
	// 첫번쨰 리스트 가져오기
	$(window).scroll(function() {
		var $window = $(this);
		var $document = $(document);
		
		var windowHeight = $window.height();
		var documentHeight = $document.height();
		var scrollTop = $window.scrollTop();
		
		if (scrollTop + windowHeight + 10 > documentHeight) {
			fetch();
		}
	});
	
	// 삭제 다이알로그 객체 만들기
	var dialogDelete = $('#dialog-delete-form').dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function() {
				// ajax 삭제....
			},
			"취소": function() {
				$(this).dialog('close');
			}
		}
	});
	
	// 글 삭제 버튼 (Live Event)
	$(document).on('click', '#list-guestbook li a', function(event){
		event.preventDefault();
		
		var no = $(this).data('no');
		$("#hidden-no").val(no);
		
		dialogDelete.dialog('open');
	});
	
	
	// form validation
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		// 이름
		var name = $("#input-name").val();
		if(!name) {
			messageBox('새글 작성', '이름은 반드시 입력해야 합니다.', function(){
				$("#input-name").focus();	
			});
			return;
		}
		
		// 비밀번호
		var password = $("#input-password").val();
		if (!password) {
			messageBox('새글 작성', '비밀번호를 반드시 입력해야 합니다.', function(){
				$("#input-password").focus();	
			});
			return;
		}
		// 내용
		var content = $("#tx-content").val();
		if (!content) {
			messageBox('새글 작성', '내용을 반드시 입력해야 합니다.', function(){
				$("#tx-content").focus();	
			});
			return;
		}
		
		vo = {};
		
		vo.name = $('#input-name').val();
		vo.password = $('#input-password').val();
		vo.message = $('#tx-content').val();
		
		$.ajax({
			url: '${pageContext.request.contextPath }/guestbook/api/add',
			dataType: 'json',
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(vo),
			success: function(response) {
				$('#input-name').val("");
				$('#input-password').val("");
				$('#tx-content').val("");
				fetch();
			}
		});
		
		//console.log("ajax insert");
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
						
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>	
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>