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
	private ReimbursementService service;
	
	public FinanceManagerController(ReimbursementService service) {
		this.service = service;
	}
	
	private ObjectMapper getObjectMapper(){
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(df);
		
		return mapper;
	}
		
	
	/**
	 * Endpoint for sending all the reimbursement data to client after logging in 
	 * as finance manager.<br>
	 * 
	 * <strong>URI: </strong><code>ersproject/manager/start</code>
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void onLoad(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User currentUser = ((User)req.getSession(false).getAttribute("current user"));
		//Map<Integer, Reimbursement> reimbursements = ReimbursementService.getReimbursementByResolverId(currentUser.getUserId());
		Map<Integer, ReimbView> reimbursements = service.getAllReimbursements();
		
		ObjectMapper mapper = getObjectMapper();
		Object[] initialData = {currentUser, reimbursements.values()};
		String respJSON = mapper.writeValueAsString(initialData);

		resp.setContentType("application/json");
		resp.getWriter().write(respJSON);
	}
	
	
	/**
	 * Endpoint for filtering reimbursments by status
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void filterReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int status = Integer.parseInt(req.getParameter("status"));
		Map<Integer, ReimbView> filteredReimb;
		
		if(status == 0)
			filteredReimb = service.getAllReimbursements();
		else
			filteredReimb = service.getReimbursementByStatus(status);
		
		if(filteredReimb != null) {
			ObjectMapper mapper = getObjectMapper();
			String respJSON = mapper.writeValueAsString(filteredReimb.values());
			resp.setContentType("application/json");
			resp.getWriter().write(respJSON);
		}
	}
	
	
	public void resolveReimbursement(HttpServletRequest req, HttpServletResponse resp) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = getObjectMapper();
		Reimbursement r = mapper.readValue(req.getInputStream(), Reimbursement.class);
		
		if(r != null) {
			service.resolveReimbursement(r);
		}
	}
}
