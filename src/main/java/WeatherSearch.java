

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import googlemap.AppUtil;

/**
 * Servlet implementation class WeatherSearch
 */
@WebServlet("/WeatherSearch")
public class WeatherSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeatherSearch() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * To fetch weather details from OpenWeather api for the given city and display the weather details of the page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String permission = (String) session.getAttribute("permission");
		
		if("admin".equalsIgnoreCase(permission) || "GoogleMapAndWeather".equalsIgnoreCase(permission)) {
		
			String cityName = request.getParameter("city");
		
			String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName +"&appid="+ AppUtil.OPEN_WEATHER_API_KEY +"&units=metric";
			URL reqUrl = new URL(url);
			HttpURLConnection postConnection = (HttpURLConnection) reqUrl.openConnection();
			postConnection.setRequestMethod("GET");
		
			int responseCode = postConnection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader buferedReader = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
				StringBuffer apiDataRes = new StringBuffer();
				String readLine = null;
			
				while((readLine = buferedReader.readLine()) != null) {
					apiDataRes.append(readLine);
				}
			
				buferedReader.close();
			
				JSONObject resJSON = new JSONObject(new String(apiDataRes));

				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
            
				out.print("<h1> Weather Details for the City : "+ cityName + "</h1>");
				JSONObject main = resJSON.optJSONObject("main");
				JSONArray weather = resJSON.optJSONArray("weather");
				JSONObject wind = resJSON.optJSONObject("wind");
				JSONObject sys = resJSON.optJSONObject("sys");
            
				out.println("<div><b> Temperature Details : </b></div>");
				out.println("<div> Current Temp : "+ main.optString("temp") +" &deg; C </div>");
				out.println("<div> Feel Like    : "+ main.optString("feels_like") +" &deg; C </div>");
				out.println("<div> Max Temp    : "+ main.optString("temp_max") +" &deg; C </div>");
				out.println("<div> Min Temp    : "+ main.optString("temp_min") +" &deg; C </div>");
				out.println("<div> Humidity    : "+ main.optString("humidity") +" &deg; C </div>");
            
				out.println("<div> <b>Weather Details : </b></div>");
				for(int i=0; i<weather.length(); i++) {
					JSONObject weaObj = weather.getJSONObject(i);
					String icon = weaObj.optString("icon");
					String imgSrc = "http://openweathermap.org/img/wn/"+ icon +"@2x.png";
            	
					out.print("<div> Weather : "+ weaObj.optString("main") +"</div>");
					out.print("<div> Weather : "+ weaObj.optString("description") +"</div>");
					out.print("<img src="+ imgSrc +">");
				}
				out.print("<div> "+ resJSON.optJSONObject("clouds").optString("all") +" % Cloudy");
            
            
				out.println("<div><b> Wind Details : </b></div>");
				out.println("<div> Wind speed : "+ wind.optString("speed") +" m/s </div>");
            
				Long sunrise = sys.getLong("sunrise") * 1000;
				Long sunset = sys.getLong("sunset") * 1000;
				out.println("<div> <b>Sunrise and sunset details : </b></div>");
				out.print("<div> Sunrise : " + new Date(sunrise).toString() + "</div>");
            	out.print("<div> Sunset : " + new Date(sunset).toString() + "</div>");
            
            	out.print("<a href=\"WeatherAPI.jsp\"> Search another city</a>");	
			}else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print("<h1> No such city found!<h1>");
				out.print("<a href=\"WeatherAPI.jsp\"> Go back to Weather API </a>");
			}
		}else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.print("<h1> User Does not have permission for this page </h1>");
			out.print("<div><a href=\"HomePage\"> Go back to Home Page</a>");
		}
	}

}
