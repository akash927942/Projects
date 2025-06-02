
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num=Integer.parseInt(request.getParameter("num"));
		
		PrintWriter out =response.getWriter();
		response.setContentType("text/html");
		
		String driver="oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(driver);
			out.println("<h2>driver is loaded</h2>");
			String url ="jdbc:oracle:thin:@localhost:1521:xe";
			String uName="system";
			String pwd="1234";
			Connection con = DriverManager.getConnection(url,uName,pwd);
			out.println("<h2>Connectoon is ready</h2>");
			
			
			String query = "select * from account where num=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, num);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				out.println("<h2>Details:  "+rs.getString(2)+","+rs.getInt(3)+"</h2>");
//				RequestDispatcher rd=request.getRequestDispatcher("success.html");
//				rd.forward(request, response);
			}
			else {
				out.println("<h2 style=\"color: red;\">invalid account Number</h2>");
				RequestDispatcher rd=request.getRequestDispatcher("Create.html");
				rd.include(request, response);
			}
			
		}catch (Exception e) {
			out.println("<h2>Exception : "+e.getMessage()+"</h2>");
		}
	}

}