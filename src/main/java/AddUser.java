

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import googlemap.AppUtil;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Add User to DB
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session = request.getSession(false);
		String currUserPermission = (String) session.getAttribute("permission");
		
		if("admin".equalsIgnoreCase(currUserPermission)){
		
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String user_id = request.getParameter("user_id");
			String home_address = request.getParameter("home_address");
			String work_address = request.getParameter("work_address");
			String permission = request.getParameter("permission");
		
			int id = fetchMaxId();
			boolean isNewEmail = checkForExistingEmail(email);
		
			if(isNewEmail) {
				try {
					Connection con = AppUtil.getDBConnection();
				
					String sqlQuery = "Insert into user (id, name, password, email, user_id, home_address, office_address, permissions) values (?, ?, ?, ?, ?, ?, ?, ?)";
				
					PreparedStatement stmt = con.prepareStatement(sqlQuery);
					stmt.setInt(1, id);
					stmt.setString(2, name);
					stmt.setString(3, password);
					stmt.setString(4, email);
					stmt.setString(5, user_id);
					stmt.setString(6, home_address);
					stmt.setString(7, work_address);
					stmt.setString(8, permission);
				
					stmt.execute();
					con.close();
				
					response.sendRedirect("http://localhost:8080/GoogleMapServlet/UserList");
				}catch(Exception ex) {
					System.out.println(ex);
				}
			}else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print("<h1> Email Already Exist!!!<h1>");
				out.print("<a href=\"UserList\"> Go back to User List</a>");
			}
		}else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.print("<h1> User Does not have permission for this page </h1>");
			out.print("<div><a href=\"HomePage\"> Go back to Home Page</a>");
		}	
	}
	
	/**
	 * To check if the email is already present in the DB*/
	private boolean checkForExistingEmail(String email) {
		try {
			Connection con = AppUtil.getDBConnection();
			
			Statement st = con.createStatement();
			String sql = "SELECT * FROM user where email=\""+ email +"\";";
			ResultSet rs = st.executeQuery(sql);
			int rows = 0;
			while(rs.next()) {
				rows = 1;
			}
			con.close();
			
			return rows == 0 ? true : false;
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	/**
	 * To get the max value of ID in DB to add new user*/
	private int fetchMaxId() {
		try {
			Connection con = AppUtil.getDBConnection();
			
			Statement st = con.createStatement();
			String sql = "SELECT MAX(id) as id FROM user;";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			int maxValue = rs.getInt("id");
			
			con.close();
			
			return maxValue + 1;
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return 0;
	}

}
