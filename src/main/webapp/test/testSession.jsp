<%@page import="com.fang.core.define.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testWebsocket</title>
<link rel="stylesheet" href="../assets/lib/bootstrap/css/bootstrap.css" type="text/css" />
</head>
<body>
	<%  
	session.setAttribute("a",  "aaa");  //把b放到session里，命名为a，  
	%>  
	<div class="container">
	<div  class="row">
	  访问的 ip 地址:   <%=request.getHeader("X-real-ip") %> <br/> 
	</div>
	<div  class="row">
	 nginx server ip is: <%=request.getRemoteAddr()%>  
	</div>
	<div  class="row">
	tomcat 地址:   <%=request.getLocalAddr() %> <br/> 
	</div>
	<div  class="row">
	 sessionId is: <%=session.getId()%>  
	</div>
	
	
	<div  class="row">
	 user is: <%=session.getAttribute(Constants.USERSESSION)%>  
	</div>
	
	</div>

<script src="../assets/js/jquery-1.9.1.min.js"></script>
<script src="../assets/js/sockjs-0.3.4.js"></script>
<script src="../assets/js/stomp.js"></script>
<script src="../assets/js/common.js"></script>
<script src="js/testWebsocket.js"></script>
</body>
</html>