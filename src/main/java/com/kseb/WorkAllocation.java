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

@WebServlet("/workallocation")
public class WorkAllocation extends HttpServlet {

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
			String cssTag = "<link rel='stylesheet' type='text/css' href='css/workAllocation.css'>";

			PrintWriter out = response.getWriter();
			DBConnection dBcon = new DBConnection();
			conn = dBcon.getConnection();

			pst = conn.prepareStatement("SELECT complaint.consumer_id, comp_id FROM complaint");
			rs = pst.executeQuery("SELECT complaint.consumer_id, comp_id FROM complaint");
			
			out.println("<html>");
		    out.println("<head><title>Title Name</title>"+cssTag+"</head>");
		    out.println("<body>");
			out.println("<form action=\"todayswork\" method=\"post\">");
			out.println("<h4>Consumer Id</h4><select name=\"sConsId\" id=\"sConsId\">");
			while (rs.next()) {
				out.println(" <option value=" + rs.getInt(2) + ">" + rs.getInt(1) + "</option>");
			}
			out.println("</select>");
			
			pst=null;
			rs=null;
			pst = conn.prepareStatement("SELECT login_username, login_id FROM login_staff WHERE login_type='lineman'");
			rs = pst.executeQuery("SELECT login_username, login_id FROM login_staff WHERE login_type='lineman'");

			out.println("<h4>Lineman</h4> <select name=\"sLineman\" id=\"sLineman\">");
			while (rs.next()) {
				out.println(" <option value=" + rs.getInt(2) + ">" + rs.getString(1) + "</option>");
			}
			out.println("</select>");
			out.println(".");
			out.println("<input type=\"submit\" value=\"Allocate\"></form>");
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
