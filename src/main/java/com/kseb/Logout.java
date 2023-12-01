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

@WebServlet("/logout")
public class Logout extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		RequestDispatcher dis = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			HttpSession session = request.getSession();
			session.invalidate();
			out.println("You are logged Out");
			dis = request.getRequestDispatcher("index.html");
			dis.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
