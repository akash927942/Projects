
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


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String uname=request.getParameter("uname");
		 String pwd=request.getParameter("pwd");
		 String cnfn=request.getParameter("cnfn");
		 PrintWriter out=response.getWriter();
		 String driver = "oracle.jdbc.driver.OracleDriver";
		 String url="jdbc:oracle:thin:@localhost:1521:xe";
//			String uname="system";
			
			if(pwd.equals(cnfn)) {
				try {
					Class.forName(driver);
					Connection con = DriverManager.getConnection(url,"system","1234");
					
					
					String query="insert into register values(?,?)";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, uname);
					ps.setString(2, pwd);
					
					 ResultSet rs=ps.executeQuery();
					if(rs.next()) {
						out.println("<h1 style='color:red'>Successfully register</h1>");
					}
					else {
						out.println("<h1 style='color:red'>Not register</h1>");
					}
				}
				catch(Exception e){
					out.println("<h1 style='color:red'> Exception " + e.getMessage() + "</h1>");
				}
			}
		
	}

}