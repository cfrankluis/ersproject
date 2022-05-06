package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.*;
import models.User;


public class LoginController {
	
	UserService service;
	
	public LoginController(UserService service) {
		this.service = service;
	}
	
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		if(!req.getMethod().equalsIgnoreCase("POST")){
			req.getRequestDispatcher("/resource/html/index.html").forward(req, resp);
			return;
		}
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User currentUser = service.validateLogin(username, password);
		
		if(currentUser != null && req.getSession(false) == null) {
			HttpSession session = req.getSession();
			session.setAttribute("current user", currentUser);
			
			switch(currentUser.getRoleId())
			{
				case 2:
					session.setAttribute("finance managers", service.getAllFinanceMangers());
					//req.getRequestDispatcher("/employee").forward(req, resp);
					resp.sendRedirect("http://localhost:8080/ersproject/employee");
					break;
				case 1: 
					//req.getRequestDispatcher("/manager").forward(req, resp);
					resp.sendRedirect("http://localhost:8080/ersproject/manager");
					break;
			}
			
		}else {
			resp.getWriter().println("<h1>INVALID CREDENTIALS</h1>");
		}
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();
			//req.getRequestDispatcher("/resource/html/index.html").forward(req, resp);
			resp.sendRedirect("http://localhost:8080/ersproject/");
		}
	}
}
