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

@WebServlet("/engineerPanel")
public class EngineerPanel extends HttpServlet{
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
			String cssTag = "<link rel='stylesheet' type='text/css' href='css/cards.css'>";
			conn=dbCon.getConnection();
			pst = conn.prepareStatement("SELECT * FROM material WHERE material_eng_accepted=0");
			rs=pst.executeQuery("SELECT * FROM material WHERE material_eng_accepted=0");
			out.println("<html>");
		    out.println("<head><title>Engineer Panel</title>"+cssTag+"</head>");
		    out.println("<body>");
		    
		    out.println("<h2>Material requests</h2>");
			
			while(rs.next()) {
				out.println("<form action=\"engineerAcceptance\" method=\"post\">");
				out.println("<h3>Request ID:</h3> <div class=\"reqIDbox\"><h2>"+rs.getInt(1)+"</h2></div>");
				out.println("<input type=\"hidden\" id=\"reqID\" name=\"reqID\" value="+rs.getInt(1)+">");
				out.println("<h3>Requests:</h3> <div class=\"req\"><h2>"+rs.getString(2)+"</h2></div>");
				out.println("<input type=\"submit\" value=\"ACCEPT\">");
				out.println(" </form>");
			}
			
			rs=pst.executeQuery("SELECT * FROM material WHERE material_eng_accepted=0");
			if(!rs.next()) {
				out.println("<h2>No Requests for Materials till Now</h2>");
			}
			out.println("</body>");
			out.println("</html>");
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
