package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Reimbursement;
import models.User;
import service.ReimbursementService;

public class EmployeeController {
	private static ObjectMapper objectMapper()
	{
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		mapper.setDateFormat(df);
		
		return mapper;
	}
	
	public static void submitReimbursement(HttpServletRequest req, HttpServletResponse resp) throws StreamReadException, DatabindException, IOException
	{
		ObjectMapper mapper = objectMapper();
		Reimbursement received = mapper.readValue(req.getInputStream(),Reimbursement.class);
		if(received != null) {
			Reimbursement response = ReimbursementService.submitReimbursement(received);
			if(response != null) {
				String respJSON = mapper.writeValueAsString(response);
				resp.setContentType("application/json");
				resp.getWriter().write(respJSON);
			}
		}
	}
	
	public static void onLoadData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User currentUser = (User)req.getSession(false).getAttribute("current user");
		Map<Integer, Reimbursement> pastReimbursements = ReimbursementService.getPastReimbursement(currentUser.getUserId());
		Map<Integer, Reimbursement> allReimbursements = ReimbursementService.getReimbursementByAuthorId(currentUser.getUserId());
		List<User> financeManagers = (List<User>)req.getSession(false).getAttribute("finance managers");
		
		Object[] dataToSend = {currentUser, pastReimbursements, allReimbursements, financeManagers};
		ObjectMapper mapper = objectMapper();
		String respJSON = mapper.writeValueAsString(dataToSend);
		
		resp.setContentType("application/json");
		resp.getWriter().write(respJSON);
	}
}
