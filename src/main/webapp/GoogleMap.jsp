<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<%@ page import="googlemap.AppUtil" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Google Maps API</title>
	<script src="https://maps.googleapis.com/maps/api/js?key=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX&callback=init" async></script>
	<script>
		var map;
		//To init google map
		function init(){
			var mapOptions ={
					zoom : 8,
					center : new google.maps.LatLng(37.985538, 23.721468)
			};
			
			map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
			
			var directionsService = new google.maps.DirectionsService();
			var directionsDisplay = new google.maps.DirectionsRenderer();
			directionsDisplay.setMap(map);
			
			var distanceMatrixService = new google.maps.DistanceMatrixService();
			
			function getDirections(){
				
				directionsDisplay.setDirections({routes: []});
				var travelTimeClear = document.getElementById("travelTime");
				travelTimeClear.innerHTML = "";
				var routeErrClear = document.getElementById("routeErr");
				routeErrClear.innerHTML = "";
				
				var source = document.getElementById("source").value;
				var desti = document.getElementById("desti").value;
				var mode = document.getElementById("travelMode").value;
				
				var directonsRequest = {
						origin : source,
						destination : desti,
						travelMode : mode
				};
				
				//To fetch directions from source to destination
				directionsService.route(directonsRequest, function(response, status){
					if(status == "OK"){
						directionsDisplay.setDirections(response);
					}else{
						var routeErr = document.getElementById("routeErr");
						routeErr.innerHTML = "<div> <b>Source / Destination address is incorrect </b></div>";
					}
				})
				
				//To fetch travel time and distacne between source and destination
				distanceMatrixService.getDistanceMatrix({
					origins : [source],
					destinations : [desti],
					travelMode : mode
				}, function(response, status){
					if(status == "OK"){
						var data = response.rows[0].elements[0];
						if(data.status == "OK"){
							var travelTime = document.getElementById("travelTime");
							var html = "<div> <label> Travel Distance </label>: <b>" + data.distance.text + "</b> </div> <div> <label> Travel Time : </label> <b> " + data.duration.text + "</b</div>";
							travelTime.innerHTML = html;
						}else{
							var travelTime = document.getElementById("travelTime");
							var html = "<div> <label> Travel Distance </label>: <b> NA </b> </div> <div> <label> Travel Time : </label> <b> NA </b</div>";
							travelTime.innerHTML = html;
						}
						
					}
				})
				
			}
			
			var buttonEle = document.getElementById("findpath");
			buttonEle.addEventListener("click", getDirections);
		}
		
		//google.maps.event.addDomListener(window, 'load', init);
	</script>
</head>
<body>
	<%
		HttpSession session = request.getSession(false);
		if(session == null){
			response.sendRedirect("http://localhost:8080/GoogleMapServlet/index.jsp");
		}else{
		String email = (String)session.getAttribute("user_email");
		ArrayList<String> address = AppUtil.fetchAddress(email);
	%>
	<div> <h1>Google Maps API module</h1></div>
	<div><a href="HomePage"> Go to Home</a></div>
	<div style="padding:12px">
		
		<label>Source : </label>
		<select name="source" id="source">
		<%
			for(int i=0;i <address.size(); i++){
				String add = address.get(i);
				%>
					<option value="<%= add %>"><%= add %></option>
				<%
			}
		%>
		</select>
	</div>
	<div style="padding:12px">
		<label> Destination : </label>
		<input name="desti" id="desti">
	</div>
	
	<div style="padding:12px">
		<label> Travel Mode :</label>
		<select name="travelMode" id="travelMode">
  				<option value="DRIVING">Driving</option>
  				<option value="WALKING">Walking</option>
  				<option value="BICYCLING">Bicycling</option>
  				<option value="TRANSIT">Transit</option>
			</select>
	</div>
	
	<div id="routeErr" style="padding:6px"></div>
	<div id="travelTime" style="padding:6px"></div>
	
	<button id="findpath" style="margin:12px"> Find Path	</button>
	
	<div id="map-canvas" style="height:600px; width:1800px"></div>
	<%} %>
</body>
</html>