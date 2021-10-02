<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Google Map and Weather App</title>
</head>
<body>
	<%
	//Login page
	HttpSession session = request.getSession(false);
	//String permission = (String)session.getAttribute("permission");
	
	if(session == null){
	%>
	<h1> Google Map and Weather App Login Form</h1>
	<form action="Login" method="POST">
		<div style="padding:15px">
			<label style="width:25px"> Email : </label>
			<input name="email" id="email"/>
		</div>
		<div style="padding:15px">
			<label style="width:25px"> Password :</label>
			<input type="password" name="password" id="password"/>
		</div>
		
		<button style="margin:15px"> Login </button>
	</form>
	<%}else{
		response.sendRedirect("http://localhost:8080/GoogleMapServlet/HomePage");
	}%>
</body>
</html>