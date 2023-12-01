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

@WebServlet("/engineerAcceptance")
public class EngineerAcceptance extends HttpServlet{

	/**
	 * 
	 */
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
			
			String reqID=request.getParameter("reqID");
			
			conn=dbCon.getConnection();
			pst = conn.prepareStatement("UPDATE material SET material_eng_accepted=1 WHERE material_id='"+reqID+"'");
			int flag=pst.executeUpdate("UPDATE material SET material_eng_accepted=1 WHERE material_id='"+reqID+"'");
			
			
			
			if(flag==1) {
				out.print("Successful");
				dis=request.getRequestDispatcher("engineerPanel");
				dis.include(request, response);
			}else {
				out.print("Try again");
				dis=request.getRequestDispatcher("engineerPanel");
				dis.include(request, response);
			}
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
