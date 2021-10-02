

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomePage
 */
@WebServlet("/HomePage")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Home page to list the modules the user can visit
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		String permission = (String)session.getAttribute("permission");
		
		out.println("<h1> Home Page </h1>");
		
		if("admin".equalsIgnoreCase(permission)) {
			out.println("<div style=\"padding:12px\"> <a href=\"UserList\"> Users List </a> </div>");
			out.println("<div style=\"padding:12px\"> <a href=\"GoogleMap.jsp\"> Google Map API Module </a> </div>");
			out.println("<div style=\"padding:12px\"> <a href=\"WeatherAPI.jsp\"> Weather API Module </a> </div>");
			out.println("<div style=\"padding:12px\"> <a href=\"logout.jsp\"> Logout </a> </div>");
		}
		else if("GoogleMap".equalsIgnoreCase(permission)) {
			out.println("<div style=\"padding:12px\"> <a href=\"GoogleMap.jsp\"> Google Map API Module </a> </div>");
			out.println("<div style=\"padding:12px\"> <a href=\"logout.jsp\"> Logout </a> </div>");
		}
		else if("GoogleMapAndWeather".equalsIgnoreCase(permission)) {
			out.println("<div style=\"padding:12px\"> <a href=\"GoogleMap.jsp\"> Google Map API Module </a> </div>");
			out.println("<div style=\"padding:12px\"> <a href=\"WeatherAPI.jsp\"> Weather API Module </a> </div>");
			out.println("<div style=\"padding:12px\"> <a href=\"logout.jsp\"> Logout </a> </div>");
		}
	}
}
