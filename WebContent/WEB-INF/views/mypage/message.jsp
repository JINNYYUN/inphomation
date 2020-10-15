<%@page import="bit.com.inpho.dto.MemberDto"%>
<%@page import="bit.com.inpho.dto.MyPageMemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>

<body>
<%
MemberDto login = (MemberDto)request.getSession().getAttribute("login");
%>
	<div style="margin-top: 100px">
	<img src="${target.profile_image}">
	${target.user_nickname}
	
	<input type="text" id="message" />
	<input type="button" id="sendBtn" value="submit"/>
	<div id="messageArea"></div>
	</div>
</body>
<script type="text/javascript">
//메시지 전송
	$("#sendBtn").click(function() {
		sendMessage();
		$.ajax({
			url:"sendMsg",
			type:"post",
			data:{"user_sender":<%=login.getUser_seq()%>, "user_target":${target.user_seq}, "msg_content":$('#message').val()},
			success:function(){
				//alert('success');
			},
			error:function(){
				alert('error');
			}
		});
		
		$('#message').val('')
	});

	let sock = new SockJS("http://192.168.0.201:8090/Inphomation/echo/");
	sock.onmessage = onMessage;
	sock.onclose = onClose;
	// 메시지 전송
	function sendMessage() {
		sock.send($("#message").val());
	}
	// 서버로부터 메시지를 받았을 때
	function onMessage(msg) {
		var data = msg.data;
		$("#messageArea").append(data + "<br/>");
	}
	// 서버와 연결을 끊었을 때
	function onClose(evt) {
		$("#messageArea").append("연결 끊김");

	}
</script>