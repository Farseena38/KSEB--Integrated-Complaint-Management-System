package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class adminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			response.setContentType("text/html");
			String cssTag = "<link rel='stylesheet' type='text/css' href='css/adminPanel.css'>";
			PrintWriter out = response.getWriter();
			DBConnection dBcon = new DBConnection();
			conn = dBcon.getConnection();

			pst = conn.prepareStatement(
					"SELECT complaint.consumer_id, comp_id,consumer_name, consumer_loc, comp_date, complaint, comp_status,comp_handle, consumer_phone FROM consumer_personal INNER JOIN complaint ON consumer_personal.consumer_id=complaint.consumer_id");
			rs = pst.executeQuery(
					"SELECT complaint.consumer_id, comp_id,consumer_name, consumer_loc, comp_date, complaint, comp_status,comp_handle, consumer_phone FROM consumer_personal INNER JOIN complaint ON consumer_personal.consumer_id=complaint.consumer_id");

			out.println("<html>");
		    out.println("<head><title>Title Name</title>"+cssTag+"</head>");
		    out.println("<body>");
		    out.println("<h2>ADMIN PANEL</h2>");
			out.println("<a href=\"workallocation\">Work Allocation</a>");
			out.println("<a class=\"reportbuttn\" href=\"logout\">logout</a></br>");
			out.println("<h3>Registered Complaints</h3>");
			out.println("<div class=\"complaintTable\">\r\n" + "<table class=\"comp\">\r\n"
					+ "            <tr>\r\n" + "<th>Consumer ID</th>\r\n" + "                <th>Complaint ID</th>\r\n"
					+ "<th>Name</th>\r\n" + "                <th>Location</th>\r\n" + "<th>Date</th>\r\n"
					+ "                <th>Complaint</th><th>Status</th><th>Handle Staff</th><th>Phone</th></tr>");
			while (rs.next()) {
				out.println(" <tr>\r\n"
						+ "                <td>"+rs.getInt(1)+"</td>\r\n"
						+ "                <td>"+rs.getInt(2)+"</td>\r\n"
						+ "                <td>'"+rs.getString(3)+"'</td>\r\n"
						+ "                <td>'"+rs.getString(4)+"'</td>\r\n"
						+ "                <td>'"+rs.getTimestamp(5)+"'</td>\r\n"
						+ "                <td>'"+rs.getString(6)+"'</td>\r\n"
						+ "                <td>'"+rs.getString(7)+"'</td>\r\n"
						+ "                <td>"+rs.getInt(8)+"</td>\r\n"
						+ "                <td>"+rs.getLong(9)+"</td></tr>");
			}
			out.println("</table></div>");
			out.println("</body>");
			out.println("</html>");
		    

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}

	}

}
