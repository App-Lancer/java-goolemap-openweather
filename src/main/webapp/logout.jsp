<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	
	//Session invalidation
	HttpSession session = request.getSession(false);
	if(session != null){
	session.invalidate(); 
	%>
	
	<h1>Logged Out</h1>
	<div> <a href="index.jsp"> Redirect to Login Page</a></div>
	<%}else{
		response.sendRedirect("http://localhost:8080/GoogleMapServlet/index.jsp");
	}%>
</body>
</html>