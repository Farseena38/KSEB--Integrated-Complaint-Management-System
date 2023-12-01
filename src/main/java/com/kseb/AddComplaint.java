package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/addComplaint")
public class AddComplaint extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	PreparedStatement pst = null;
	ResultSet rs = null;

	Connection conn=null;
	RequestDispatcher dis=null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		DBConnection dbCon = new DBConnection();
		try {
			
			HttpSession session =request.getSession(false);
			String userName=(String)session.getAttribute("cons_id");
			
			
			conn=dbCon.getConnection();
			
			pst = conn.prepareStatement("SELECT cons_id FROM login_consumer WHERE cons_username='"+userName+"'");
			rs = pst.executeQuery("SELECT cons_id FROM login_consumer WHERE cons_username='"+userName+"'");
			
			rs.next();
			int consId=rs.getInt(1);
			String complaint=request.getParameter("complaint");
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
			Calendar cal = Calendar.getInstance();  
			java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());

			pst = conn.prepareStatement("INSERT INTO complaint(consumer_id,complaint,comp_date)VALUES("+consId+",'"+complaint+"','"+timestamp+"')");
			int flag= pst.executeUpdate("INSERT INTO complaint(consumer_id,complaint,comp_date)VALUES("+consId+",'"+complaint+"','"+timestamp+"')");
			
			if(flag==1) {
				out.print("Complaint Registered Successfully");
				dis=request.getRequestDispatcher("consumerPanel.html");
				dis.include(request, response);
			}else {
				out.print("Can't Register Complaint, Try again");
				dis=request.getRequestDispatcher("consumerPanel.html");
				dis.include(request, response);
			}
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
