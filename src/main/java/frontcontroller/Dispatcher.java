package frontcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.*;

import service.UserService;
import dao.UserDao;

public class Dispatcher {
	
	private static UserDao dao = new UserDao();
	private static UserService service = new UserService(dao);
	private static LoginController loginController = new LoginController(service);
	
	public static void reqDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		switch(req.getRequestURI())
		{
		//Login URIs
		case "/ersproject/login":
			loginController.login(req, resp);
			break;
		case "/ersproject/logout":
			loginController.logout(req, resp);
			break;
			
		//Employee URIs	
		case "/ersproject/employee":
			req.getRequestDispatcher("resource/html/employee.html").forward(req, resp);
			break;
		case "/ersproject/employee/start":
			EmployeeController.onLoadData(req, resp);
			break;
		case "/ersproject/employee/add":
			EmployeeController.submitReimbursement(req, resp);
			break;
//		case "/ersproject/employee/all":
//			EmployeeController.showAllreimbursement(req, resp);
//			break;
//		case "/ersproject/employee/past":
//			EmployeeController.showPastreimbursement(req, resp);
//			break;
			
		//Finance manager URI	
		case "/ersproject/manager":
			req.getRequestDispatcher("resource/html/financemanager.html").forward(req, resp);
			break;
		case "/ersproject/manager/start":
			FinanceManagerController.onLoad(req, resp);
			break;
		case "/ersproject/manager/resolve":
			FinanceManagerController.resolveReimbursement(req, resp);
			break;
		case "/ersproject/manager/filter":
			FinanceManagerController.filterReimbursement(req, resp);
			break;
			
		//Default	
		default:
			resp.getWriter().println("<h1>INVALID URI: " + req.getRequestURI() + "</h1>");
			break;
		}
	}
}
