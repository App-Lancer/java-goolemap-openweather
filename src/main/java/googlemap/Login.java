package googlemap;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Login validation page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
		
			boolean validateLogin = AppUtil.validateLogin(email, password);
		
			if(validateLogin) {
				HttpSession newSess = request.getSession();
				newSess.setAttribute("user_email", email);
				String getPermission = AppUtil.getPermission(email);
				newSess.setAttribute("permission", getPermission);
				response.sendRedirect("http://localhost:8080/GoogleMapServlet/HomePage");
			}else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("No such user found");
				out.println("<a href=\"index.jsp\">Back to Login Page</a>");
			}
		
	}

}
