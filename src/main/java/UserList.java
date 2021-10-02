

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class UserList
 */
@WebServlet("/UserList")
public class UserList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * To fetch all the users in DB and show them in UI for users with admin permission
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session = request.getSession(false);
		String permission = (String) session.getAttribute("permission");
		
		if("admin".equalsIgnoreCase(permission)) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("<head>");
			out.println("<style type=\"text/css\">");
			out.println("table, th, td{");
			out.println("border : 1px solid black;");
			out.println("padding : 5px");
			out.println("}");
			out.println("</style>");
			out.print("</head>");
			out.print("<h1> User List </h1>");
			out.println("<a href=\"AddUser.jsp\">Add User</a>");
			out.print("<table>");
			out.print("<tr>");
			out.print("<th> ID </th> <th> Name </th> <th> Email </th> <th> USER ID </th> <th> Home Address </th> <th> Work Address </th> <th> Permission </th>");
			out.print("</tr>");
		
			try {
				Connection con = AppUtil.getDBConnection();
			
				Statement st = con.createStatement();
				String sql = "SELECT * FROM user;";
				ResultSet rs = st.executeQuery(sql);
			
				while(rs.next()) {
					out.print("<tr>");
					out.print("<td> "+ rs.getString("id") +" </td> <td> "+ rs.getString("name") +" </td> <td> "+ rs.getString("email") +" </td> <td> "+ rs.getString("user_id") +" </td> <td> "+ rs.getString("home_address") +" </td> <td> "+ rs.getString("office_address") +" </td> <td> "+ rs.getString("permissions") +" </td>");
					out.print("</tr>");
				}
			
				out.println("</table>");
				
				out.print("<div> <a href=\"HomePage\"> Go to Home</a> </div>");
				con.close();
			}catch(Exception ex) {
				System.out.println(ex);
			}
		}else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.print("<h1> User Does not have permission for this page </h1>");
			out.print("<div><a href=\"HomePage\"> Go back to Home Page</a>");
		}
	}
}
