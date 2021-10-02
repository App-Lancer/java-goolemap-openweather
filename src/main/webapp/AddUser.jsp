<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add User Form</title>
<script>
	// To validate form data before submitting the data to servlet
	function validateForm(){
		var isValid = true;
		var nameEle = document.getElementById("name");
		if(nameEle.value.length < 3){
			isValid = false;
			var err = document.createElement("LABEL");
			err.innerHTML = "Name is to short!!!"
			err.style.color = 'red';
			document.getElementById("name-div").appendChild(err);
			return isValid;
		}
		
		var passowordEle = document.getElementById("password");
		if(passowordEle.value.length < 3){
			isValid = false;
			var err = document.createElement("LABEL");
			err.innerHTML = "Passowrd is to short!!!"
			err.style.color = 'red';
			document.getElementById("passowrd-div").appendChild(err);
			return isValid;
		}
		
		return isValid;
	}
</script>
</head>
<body>
	<%
	//If no session exist the page will be directed to login
	HttpSession session = request.getSession(false);
	if(session == null){
		response.sendRedirect("http://localhost:8080/GoogleMapServlet/index.jsp");
	}else{
	String permission = (String)session.getAttribute("permission");
	
	if("admin".equalsIgnoreCase(permission)){
	%>
	<h1> Add User Form</h1>
	<form action="AddUser" method="POST" onsubmit="return validateForm()">
		<div id="name-div" style="padding:12px">
			<label>Name : </label>
			<input name="name" id="name">
		</div>
		
		<div id="passowrd-div" style="padding:12px">
			<label >Password : </label>
			<input type="password" name="password" id="password">
		</div>
		
		<div style="padding:12px">
			<label>Email : </label>
			<input type="email" name="email" id="email" required>
		</div>
		
		<div style="padding:12px">
			<label>User ID : </label>
			<input type="number" name="user_id" id="user_id">
		</div>
		
		<div style="padding:12px">
			<label>Home Address : </label>
			<input name="home_address" id="home_address">
		</div>
		
		<div style="padding:12px">
			<label>Work Address : </label>
			<input name="work_address" id="work_address">
		</div>
		
		<div style="padding:12px">
			<label>Permission : </label>
			<select name="permission" id="permission">
  				<option value="Admin">Admin</option>
  				<option value="GoogleMap">GoogleMap</option>
  				<option value="GoogleMapAndWeather">GoogleMapAndWeather</option>
			</select>
		</div>
		
		<button style="margin:12px">Add User</button>
		
		<div><a href="HomePage"> Go to Home</a></div>
	</form>
	<% 
	}else{
		%>
		<h1> User does not have permission</h1>
		<div><a href="HomePage"> Go to HomePage</a></div>
	<%
	}}
	%>
</body>
</html>