
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DepositServ")
public class DepositServ extends HttpServlet {
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
			
			String query = "UPDATE ACCOUNT SET balance = balance+? WHERE num=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,Amt);
			ps.setInt(2,num);
			ps.executeUpdate();
			out.println("<h2>Amount Deposited</h2>");
			
		}catch (Exception e) {
			out.println("<h2>Exception : "+e.getMessage()+"</h2>");
		}
	}

}