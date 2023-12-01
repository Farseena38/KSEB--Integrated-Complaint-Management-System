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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/fetch")
public class LoginCode extends HttpServlet {

	private static final long serialVersionUID = 1L;

	PreparedStatement pst = null;
	ResultSet rs = null;

	RequestDispatcher dis = null;
	Connection conn = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		DBConnection dbCon = new DBConnection();
		try {
			String uname = request.getParameter("uname");
			String upass = request.getParameter("upass");
			String uselect = request.getParameter("uselect");

			conn=dbCon.getConnection();

			if (uselect.equals("admin")) {
				pst = conn.prepareStatement("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				rs = pst.executeQuery("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				if (rs.next()) {
					HttpSession session=request.getSession();
					session.setAttribute("cons_id", uname);
					dis = request.getRequestDispatcher("/admin");
					dis.forward(request, response);
				} else {
					out.print("Wrong Username or Password");
					dis = request.getRequestDispatcher("index.html");
					dis.include(request, response);
				}
			} else if (uselect.equals("consumer")) {
				pst = conn.prepareStatement("SELECT cons_username FROM login_consumer WHERE cons_username='" + uname
						+ "' and cons_password='" + upass + "'");
				rs = pst.executeQuery("SELECT cons_username FROM login_consumer WHERE cons_username='" + uname
						+ "' and cons_password='" + upass + "'");

				if (rs.next()) {
					String userName = rs.getString(1);
					
					HttpSession session=request.getSession();
					session.setAttribute("cons_id", userName);
					
					dis = request.getRequestDispatcher("consumerPanel.html");
					dis.forward(request, response);
				} else {
					out.print("Wrong Username or Password");
					dis = request.getRequestDispatcher("index.html");
					dis.include(request, response);
				}
			} else if (uselect.equals("lineman")) {
				pst = conn.prepareStatement("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				rs = pst.executeQuery("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				if (rs.next()) {
					Cookie ck = new Cookie("cons_id", uname);
					response.addCookie(ck);
					HttpSession session=request.getSession();
					session.setAttribute("cons_id", uname);
					dis = request.getRequestDispatcher("/lineman");
					dis.forward(request, response);
				} else {
					out.print("Wrong Username or Password");
					dis = request.getRequestDispatcher("index.html");
					dis.include(request, response);
				}
			} else if (uselect.equals("manager")) {
				pst = conn.prepareStatement("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				rs = pst.executeQuery("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				if (rs.next()) {
					
					dis = request.getRequestDispatcher("managerPanel");
					dis.forward(request, response);
				} else {
					out.print("Wrong Username or Password");
					dis = request.getRequestDispatcher("index.html");
					dis.include(request, response);
				}
			} else if (uselect.equals("engineer")) {
				pst = conn.prepareStatement("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				rs = pst.executeQuery("SELECT login_id FROM login_staff WHERE login_username='" + uname
						+ "' and login_password='" + upass + "' and login_type='" + uselect + "'");
				if (rs.next()) {
					
					Cookie ck = new Cookie("cons_id", uname);
					response.addCookie(ck);
					dis = request.getRequestDispatcher("engineerPanel");
					dis.forward(request, response);
				} else {
					out.print("Wrong Username or Password");
					dis = request.getRequestDispatcher("index.html");
					dis.include(request, response);
				}
			}

			conn.close();
			
		} catch (SQLException sql) {
			sql.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
