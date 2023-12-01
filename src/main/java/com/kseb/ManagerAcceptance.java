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

@WebServlet("/managerAcceptance")
public class ManagerAcceptance extends HttpServlet{

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
			System.out.println(reqID);
			
			conn=dbCon.getConnection();
			
			String reqId=request.getParameter("reqID");
			
			pst = conn.prepareStatement("SELECT fk_cons_id FROM material WHERE material_id='"+reqId+"'");
			rs = pst.executeQuery("SELECT fk_cons_id FROM material WHERE material_id='"+reqId+"'");
			
			rs.next();
			int cons_id=rs.getInt(1);
			
			pst = conn.prepareStatement("UPDATE complaint SET comp_status=\"Materials Allotted\" WHERE consumer_id='"+cons_id+"'");
			int flag=pst.executeUpdate("UPDATE complaint SET comp_status=\"Materials Allotted\" WHERE consumer_id='"+cons_id+"'");
			
			pst=null;
			pst = conn.prepareStatement("UPDATE material SET material_eng_accepted=2 WHERE material_id='"+reqID+"'");
			pst.executeUpdate("UPDATE material SET material_eng_accepted=2 WHERE material_id='"+reqID+"'");
			
			if(flag==1) {
				out.print("Successful");
				dis=request.getRequestDispatcher("managerPanel");
				dis.include(request, response);
			}else {
				out.print("Try again");
				dis=request.getRequestDispatcher("managerPanel");
				dis.include(request, response);
			}
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
