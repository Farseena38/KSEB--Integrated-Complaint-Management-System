package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/todayswork")
public class TodaysWork extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	RequestDispatcher dis=null;
	
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		DBConnection dBcon = new DBConnection();
		conn = dBcon.getConnection();
		
		String sConsId=request.getParameter("sConsId");
		String sLineman=request.getParameter("sLineman");
		System.out.println(sConsId+"+++"+sLineman);

		try {
			
			pst = conn.prepareStatement("INSERT INTO lineman_duty(comp_id,fk_login_id) VALUES("+sConsId+","+sLineman+")");
			int flag= pst.executeUpdate("INSERT INTO lineman_duty(comp_id,fk_login_id) VALUES("+sConsId+","+sLineman+")");


			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			if(flag==1) {
				out.print("Work Allocated Successfully");
				System.out.println("dddd");
				
				  dis=request.getRequestDispatcher("workallocation");
				  dis.include(request,response);
				 
			}else {
				out.print("Can't Allocate, Try again");
				dis=request.getRequestDispatcher("workallocation");
				dis.include(request, response);
			}
			
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}

}
