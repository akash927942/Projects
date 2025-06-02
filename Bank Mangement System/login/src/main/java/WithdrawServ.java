
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/WithdrawServ")
public class WithdrawServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num=Integer.parseInt(request.getParameter("num"));
        int Amt=Integer.parseInt(request.getParameter("amt"));
		
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
			
			String query = "select balance from Account where num=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, num);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt(1)>=Amt) {
					String query1 = "UPDATE ACCOUNT SET balance = balance-? WHERE num=?";
					PreparedStatement ps1 = con.prepareStatement(query1);
					ps1.setInt(1, Amt);
					ps1.setInt(2, num);
					ps1.executeUpdate();
					out.println("<h2>withdraw successful</h2>");
				}
				else {
					out.println("<h2>Insufficient Fund</h2>");
				}
			}
			else {
				out.println("<h2>invalid account Number</h2>");
			}
			
		}catch (Exception e) {
			out.println("<h2>Exception : "+e.getMessage()+"</h2>");
		}
	}

}