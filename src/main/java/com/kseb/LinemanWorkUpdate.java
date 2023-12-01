package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/workprogress")
public class LinemanWorkUpdate extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PreparedStatement pst = null;
	ResultSet rs = null;

	Connection conn=null;
	RequestDispatcher dis=null;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DBConnection dbCon = new DBConnection();
		try {
			
			/*
			 * Cookie ck[]=request.getCookies(); String userName=ck[0].getValue();
			 */
			conn=dbCon.getConnection();
			String cssTag = "<link rel='stylesheet' type='text/css' href='css/workAllocation.css'>";
			pst = conn.prepareStatement("SELECT consumer_id FROM complaint");
			rs = pst.executeQuery("SELECT consumer_id FROM complaint");
			out.println("<html>");
		    out.println("<head><title>Title Name</title>"+cssTag+"</head>");
		    out.println("<body>");
			out.println("<form action=\"statusupdate\" method=\"post\">");
			
			out.println("<h3>CONSUMER ID</h3><select name=\"consumerID\" id=\"consumerID\">\r\n");
			while(rs.next()) {
				out.println("<option value="+rs.getInt(1)+">"+rs.getInt(1)+"</option>");
			}
			out.println( " </select>");
			
			
			out.println("<h3>STATUS</h3><select name=\"statusflag\" id=\"statusflag\">\r\n"
					+ "            <option value=\"Open\">Open</option>\r\n"
					+ "            <option value=\"Pending\">Pending</option>\r\n"
					+ "            <option value=\"Closed\">Closed</option>\r\n"
					+ "        </select>");
			
			out.println("<h3>Materials required</h3><textarea name=\"materials\" id=\"materials\" cols=\"30\" rows=\"10\" placeholder=\"Enter required Materials\"></textarea>");
			out.println("<input type=\"submit\" value=\"Submit\">");
			out.println(" </form>");
			
			
			out.println("</body>");
			out.println("</html>");
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
