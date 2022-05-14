package frontcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.*;

import service.*;
import dao.ReimbursementDao;
import dao.UserDao;

public class Dispatcher {
	
	private static UserDao userDao = new UserDao();
	private static UserService service = new UserService(userDao);
	private static LoginController loginController = new LoginController(service);
	
	private static ReimbursementDao reimbDao = new ReimbursementDao();
	private static ReimbursementService reimbService = new ReimbursementService(reimbDao);
	private static EmployeeController empCont = new EmployeeController(reimbService);
	private static FinanceManagerController fmCont = new FinanceManagerController(reimbService);
	
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
			empCont.onLoadData(req, resp);
			break;
		case "/ersproject/employee/add":
			empCont.submitReimbursement(req, resp);
			break;
			
		//Finance manager URI	
		case "/ersproject/manager":
			req.getRequestDispatcher("resource/html/financemanager.html").forward(req, resp);
			break;
		case "/ersproject/manager/start":
			fmCont.onLoad(req, resp);
			break;
		case "/ersproject/manager/resolve":
			fmCont.resolveReimbursement(req, resp);
			break;
		case "/ersproject/manager/filter":
			fmCont.filterReimbursement(req, resp);
			break;
			
		//Default	
		default:
			resp.getWriter().println("<h1>INVALID URI: " + req.getRequestURI() + "</h1>");
			break;
		}
	}
}
