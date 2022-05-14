package controllerTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.LoginController;
import models.User;
import service.UserService;

class LoginControllerTest {
	
	HttpServletRequest req;
	HttpServletResponse resp;
	HttpSession session;
	UserService service;
	LoginController controller;
	
	@BeforeEach
	void setUp() throws Exception {
		req = mock(HttpServletRequest.class);
		resp = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		service = mock(UserService.class);
		controller = new LoginController(service);
		when(req.getMethod()).thenReturn("post");
		when(req.getParameter("username")).thenReturn("Hello");
		when(req.getParameter("password")).thenReturn("World");
	}

	@Test
	void badLoginTest() throws IOException, ServletException {
		//ARRANGE
		when(resp.getWriter()).thenReturn(new PrintWriter("src\\test\\resources\\respfile"));
		when(service.validateLogin("Hello","World")).thenReturn(null);
		
		//ACT
		controller.login(req,resp);
		
		//ASSERT
		verify(req, times(0)).getSession();
		verify(resp, times(1)).getWriter();
	}
	
	@Test
	void managerLoginTest() throws ServletException, IOException {
		//ARRANGE
		User manager = new User();
		manager.setRoleId(1);
		when(service.validateLogin("Hello", "World")).thenReturn(manager);
		when(req.getSession()).thenReturn(session);
		
		//ACT
		controller.login(req, resp);
		
		//ASSERT
		verify(resp,times(1)).sendRedirect("http://localhost:8080/ersproject/manager");
	}
	
	@Test
	void employeeLoginTest() throws ServletException, IOException {
		//ARRANGE
		User employee = new User();
		employee.setRoleId(2);
		when(service.validateLogin("Hello", "World")).thenReturn(employee);
		when(req.getSession()).thenReturn(session);
		
		//ACT
		controller.login(req, resp);
		
		//ASSERT
		verify(resp,times(1)).sendRedirect("http://localhost:8080/ersproject/employee");
	}
	
	@Test
	void logoutTest() throws ServletException, IOException {
		//ARRANGE
		when(req.getSession(false)).thenReturn(session);
		
		//ACT
		controller.logout(req, resp);
		
		//ASSERT
		verify(session, times(1)).invalidate();
	}

}
