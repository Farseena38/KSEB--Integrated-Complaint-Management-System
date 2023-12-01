package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/viewreport")
public class ViewReport extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void service(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			response.setContentType("text/html");
			String cssTag = "<link rel='stylesheet' type='text/css' href='css/LinemanPanel.css'>";
			PrintWriter out = response.getWriter();
			DBConnection dBcon = new DBConnection();
			conn = dBcon.getConnection();

			HttpSession session =request.getSession(false);
			String uname=(String)session.getAttribute("cons_id");
			
			
			pst = conn.prepareStatement("SELECT consumer_id FROM consumer_personal WHERE consumer_name='"+uname+"'");
			rs=pst.executeQuery("SELECT consumer_id FROM consumer_personal WHERE consumer_name='"+uname+"'");
			rs.next();
			int cons_id=rs.getInt(1);
			
			pst = conn.prepareStatement("SELECT comp_date,complaint,comp_status FROM complaint WHERE consumer_id="+cons_id+"");
			rs = pst.executeQuery("SELECT comp_date,complaint,comp_status FROM complaint WHERE consumer_id="+cons_id+"");
			
			
			
			out.println("<html>");
		    out.println("<head><title>Reports</title>"+cssTag+"</head>");
		    out.println("<body>");
			out.println("<h2>Complaints Status</h2>");

			out.println("<div class=\"complaintTable\">\r\n" + "<table class=\"comp\">"
					+ "<tr><th>Complaint date</th>"
					+ "<th>Complaint</th>"
					+ "<th>Status</th>"
					+ "</tr>");
			while (rs.next()) {
				out.println(" <tr>\r\n"
						+ "                <td>"+rs.getTimestamp(1)+"</td>\r\n"
								+ "                <td>'"+rs.getString(2)+"'</td>\r\n"
						+ "                <td>"+rs.getString(3)+"</td>\r\n");
			}
			out.println("</table></div>");
			out.println("</body>");
			out.println("</html>");
			
			conn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}

	}
	
}
