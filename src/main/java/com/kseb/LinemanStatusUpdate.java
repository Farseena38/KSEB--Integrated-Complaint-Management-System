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

@WebServlet("/statusupdate")
public class LinemanStatusUpdate extends HttpServlet{
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
			
			String statusflag=request.getParameter("statusflag");
			String consumerID=request.getParameter("consumerID");
			String materials=request.getParameter("materials");
			
			conn=dbCon.getConnection();
			pst = conn.prepareStatement("UPDATE complaint SET comp_status='"+statusflag+"' WHERE consumer_id="+consumerID+"");
			pst.executeUpdate("UPDATE complaint SET comp_status='"+statusflag+"' WHERE consumer_id="+consumerID+"");
			
			pst=null;
			rs=null;
			System.out.println(materials);

			pst = conn.prepareStatement("INSERT INTO material(material,fk_cons_id)VALUES('"+materials+"','"+consumerID+"')");
			int flag= pst.executeUpdate("INSERT INTO material(material,fk_cons_id)VALUES('"+materials+"','"+consumerID+"')");
			
			if(flag==1) {
				out.print("Successful");
				dis=request.getRequestDispatcher("workallocation");
				dis.include(request, response);
			}else {
				out.print("Try again");
				dis=request.getRequestDispatcher("workallocation");
				dis.include(request, response);
			}
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
