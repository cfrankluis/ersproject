package frontcontroller;

import models.User;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="MasterServlet", urlPatterns= {"/login","/logout","/employee/*","/manager/*"})
public class MasterServlet extends HttpServlet {
	
	private boolean isAllowed(HttpServletRequest r, User u)
	{
		String keyWord = (r.getRequestURI().split("/"))[2];
		return (keyWord.equals("employee") && u.getRoleId() == 2)
				|| (keyWord.equals("manager") && u.getRoleId() == 1);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession(false) != null){
			User currentUser = (User)req.getSession(false).getAttribute("current user");
			if(isAllowed(req,currentUser) || req.getRequestURI().equals("/ersproject/logout"))
				Dispatcher.reqDispatcher(req, resp);
			else
				resp.getWriter().println("<h1>NOT ALLOWED!</h1>");
		}
		else if(req.getRequestURI().equals("/ersproject/login")) {
			Dispatcher.reqDispatcher(req, resp);
		}else {
			resp.getWriter().println("<h1>PLEASE LOG IN!</h1>");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
