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

@WebServlet("/lineman")
public class LinemanPanel extends HttpServlet{
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

			Cookie ck[]=request.getCookies();
			String userName=ck[0].getValue();
			pst = conn.prepareStatement("SELECT login_id FROM login_staff WHERE login_username='"+userName+"'");
			rs = pst.executeQuery("SELECT login_id FROM login_staff WHERE login_username='"+userName+"'");
			
			rs.next();
			int login_id=rs.getInt(1);
			
			pst=null;
			rs=null;
			
			pst = conn.prepareStatement("SELECT complaint.comp_id,"
					+ "fk_login_id,"
					+ " consumer_personal.consumer_id,"
					+ " complaint,"
					+ " comp_date,"
					+ " comp_status,"
					+ "consumer_name,"
					+ " consumer_phone,"
					+ "consumer_loc,"
					+ "consumer_post\r\n"
					+ "FROM lineman_duty INNER JOIN complaint\r\n"
					+ "ON complaint.comp_id=lineman_duty.comp_id\r\n"
					+ "AND fk_login_id="+login_id+"\r\n"
					+ "INNER JOIN consumer_personal\r\n"
					+ "ON consumer_personal.consumer_id=complaint.consumer_id");
			rs = pst.executeQuery("SELECT complaint.comp_id,fk_login_id, consumer_personal.consumer_id, complaint, comp_date, comp_status,consumer_name, consumer_phone,consumer_loc,consumer_post\r\n"
					+ "FROM lineman_duty INNER JOIN complaint\r\n"
					+ "ON complaint.comp_id=lineman_duty.comp_id\r\n"
					+ "AND fk_login_id="+login_id+"\r\n"
					+ "INNER JOIN consumer_personal\r\n"
					+ "ON consumer_personal.consumer_id=complaint.consumer_id");
			out.println("<html>");
		    out.println("<head><title>Title Name</title>"+cssTag+"</head>");
		    out.println("<body>");
			out.println("<h2>Allocated Work</h2>");
			out.println("<a href=\"workprogress\">Update Work status</a>");
			out.println("<a class=\"reportbuttn\" href=\"logout\">logout</a></br>");
			out.println("<div class=\"complaintTable\">\r\n" + "<table class=\"comp\">"
					+ "<tr><th>Consumer ID</th>"
					+ "<th>Complaint ID</th>"
					+ "<th>Date</th>"
					+ "<th>Complaint</th>"
					+ "<th>Status</th>"
					+ "<th>Name</th>"
					+ "<th>Phone</th>"
					+ "<th>location</th>"
					+ "<th>Post</th>"
					+ "</tr>");
			while (rs.next()) {
				out.println(" <tr>\r\n"
						+ "                <td>"+rs.getInt(3)+"</td>\r\n"
								+ "                <td>'"+rs.getInt(1)+"'</td>\r\n"
						+ "                <td>"+rs.getTimestamp(5)+"</td>\r\n"
						+ "                <td>'"+rs.getString(4)+"'</td>\r\n"
						+ "                <td>'"+rs.getString(6)+"'</td>\r\n"
						+ "                <td>'"+rs.getString(7)+"'</td>\r\n"
						+ "                <td>'"+rs.getLong(8)+"'</td>\r\n"
						+ "                <td>'"+rs.getString(9)+"'</td>\r\n"
						+ "                <td>"+rs.getInt(10)+"</td>\r\n");
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
