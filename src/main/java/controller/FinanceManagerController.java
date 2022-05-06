package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.*;
import service.ReimbursementService;

public class FinanceManagerController {
	private static ObjectMapper getObjectMapper(){
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(df);
		
		return mapper;
	}
		
	public static void onLoad(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User currentUser = ((User)req.getSession(false).getAttribute("current user"));
		//Map<Integer, Reimbursement> reimbursements = ReimbursementService.getReimbursementByResolverId(currentUser.getUserId());
		Map<Integer, ReimbView> reimbursements = ReimbursementService.getAllReimbursements();
		
		ObjectMapper mapper = getObjectMapper();
		Object[] initialData = {currentUser, reimbursements.values()};
		String respJSON = mapper.writeValueAsString(initialData);

		resp.setContentType("application/json");
		resp.getWriter().write(respJSON);
	}
	
	public static void filterReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int status = Integer.parseInt(req.getParameter("status"));
		Map<Integer, ReimbView> filteredReimb;
		
		if(status == 0)
			filteredReimb = ReimbursementService.getAllReimbursements();
		else
			filteredReimb = ReimbursementService.getReimbursementByStatus(status);
		
		if(filteredReimb != null) {
			ObjectMapper mapper = getObjectMapper();
			String respJSON = mapper.writeValueAsString(filteredReimb.values());
			resp.setContentType("application/json");
			resp.getWriter().write(respJSON);
		}
	}
	
	public static void resolveReimbursement(HttpServletRequest req, HttpServletResponse resp) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = getObjectMapper();
		Reimbursement r = mapper.readValue(req.getInputStream(), Reimbursement.class);
		
		if(r != null) {
			ReimbursementService.resolveReimbursement(r);
		}
	}
}
