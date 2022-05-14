package service;

import models.ReimbView;
import models.Reimbursement;
import dao.ReimbursementDao;

import java.util.Map;

public class ReimbursementService {
	
	private ReimbursementDao dao;
	
	public ReimbursementService(ReimbursementDao dao) {
		this.dao = dao;
	}
	
	public Reimbursement submitReimbursement(Reimbursement r) {
		return dao.insertReimbursement(r);
	}
	
	public boolean resolveReimbursement(Reimbursement r){
		return dao.updateReimbursementStatus(r);
	}
	
	public Map<Integer, Reimbursement> getReimbursementByAuthorId(int authorId){
		return dao.selectReimbursementByAuthorId(authorId);
	}
	
	public Map<Integer, Reimbursement> getPastReimbursement(int authorId){
		return dao.selectPastReimbursement(authorId);
	}
		
	public Map<Integer, ReimbView> getReimbursementByStatus(int status){
		return dao.selectReimbursementsByStatus(status);
	}
	
	public Map<Integer, ReimbView> getAllReimbursements(){
		return dao.selectAllReimbursements();
	}
}
