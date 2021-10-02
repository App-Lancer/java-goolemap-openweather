<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Weather API Integration</title>
</head>
<body>
	<%
	//Weather api data
	HttpSession session = request.getSession(false);
	if(session == null){
		response.sendRedirect("http://localhost:8080/GoogleMapServlet/index.jsp");
	}else{
	String permission = (String)session.getAttribute("permission");
	
	if("admin".equalsIgnoreCase(permission) || "GoogleMapAndWeather".equalsIgnoreCase(permission)){
	%>
	<div><a href="HomePage"> Go to Home</a> </div>
	<form action="WeatherSearch" method="POST">
		<label> Enter City Name : </label>
		<input name="city" id="city">
		
		<button> Get Weather Data</button>
	</form>
	<%}else{
		%>
			<h1> User does not have permission</h1>
			<div> <a href="HomePage"></a></div>
		<% 
	}}%>
</body>
</html>