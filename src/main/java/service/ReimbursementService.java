package service;

import models.ReimbView;
import models.Reimbursement;
import static dao.ReimbursementDao.*;

import java.util.Map;

public class ReimbursementService {
	
	public static Reimbursement submitReimbursement(Reimbursement r) {
		return insertReimbursement(r);
	}
	
	public static boolean resolveReimbursement(Reimbursement r){
		return updateReimbursementStatus(r);
	}
	
	public static Map<Integer, Reimbursement> getReimbursementByAuthorId(int authorId){
		return selectReimbursementByAuthorId(authorId);
	}
	
	public static Map<Integer, Reimbursement> getPastReimbursement(int authorId){
		return selectPastReimbursement(authorId);
	}
		
	public static Map<Integer, ReimbView> getReimbursementByStatus(int status){
		return selectReimbursementsByStatus(status);
	}
	
	public static Map<Integer, ReimbView> getAllReimbursements(){
		return selectAllReimbursements();
	}
}
