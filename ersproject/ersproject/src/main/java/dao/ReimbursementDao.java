package dao;

import static factory.ConnectionFactory.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.Reimbursement;
import models.ReimbView;

public class ReimbursementDao {
	
	/**
	 * Inserts a reimbursement to the reimbursement table in the database.
	 * 
	 * @param
	 * 	A reimbursement object containing the
	 * 	following data: 
	 * <code>amount</code>, 
	 * <code>description</code>, 
	 * <code>authorId</code>, 
	 * <code>resolverId</code>, 
	 * <code>TypeId</code>, 
	 * @return
	 * 	A reimbursement object containing all the data about
	 * the inserted reimbursement.
	 */
	public Reimbursement insertReimbursement(Reimbursement r)
	{
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "INSERT INTO reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author_fk, reimb_resolver_fk, reimb_status_fk, reimb_type_fk)"
									+ "VALUES (?,CURRENT_DATE,?,?,?,1,?) RETURNING reimb_id, reimb_submitted";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			ps.setDouble(1, r.getAmount());
			ps.setString(2, r.getDescription());
			ps.setInt(3, r.getAuthorId());
			ps.setInt(4, r.getResolverId());
			ps.setInt(5, r.getTypeId());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("reimb_id");
				Date submitted = rs.getDate("reimb_submitted");
				r.setId(id);
				r.setSubmitted(submitted);
				r.setStatusId(1);
				return r;
			} else
				return null;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateReimbursementStatus(Reimbursement r)
	{
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "UPDATE reimbursement SET reimb_resolved = Current_date, reimb_status_fk = ? WHERE reimb_id = ?";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			ps.setInt(1, r.getStatusId());
			ps.setInt(2, r.getId());
			
			int result = ps.executeUpdate();
			return result == 1;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	private Map<Integer, Reimbursement> mapResult(ResultSet rs) throws SQLException
	{
		Map<Integer, Reimbursement> reimbursementMap = new HashMap<>();
		while(rs.next()){
			int id = rs.getInt("reimb_id");
			double amount = rs.getDouble("reimb_amount");
			Date submitted = rs.getDate("reimb_submitted");
			Date resolved = rs.getDate("reimb_resolved");
			String description = rs.getString("reimb_description");
			int authorId = rs.getInt("reimb_author_fk");
			int resolverId = rs.getInt("reimb_resolver_fk");
			int statusId = rs.getInt("reimb_status_fk");
			int typeId = rs.getInt("reimb_type_fk");
			
			reimbursementMap.put(id, new Reimbursement(id, amount, submitted, resolved, description, authorId, resolverId, statusId, typeId));
		}
		
		return reimbursementMap;
	}
	
	private Map<Integer, ReimbView> mapView(ResultSet rs) throws SQLException
	{
		Map<Integer, ReimbView> reimbursementMap = new HashMap<>();
		while(rs.next()){
			int id = rs.getInt("id");
			double amount = rs.getDouble("amount");
			Date submitted = rs.getDate("date_submitted");
			Date resolved = rs.getDate("date_resolved");
			String description = rs.getString("description");
			String authorFirstname=rs.getString("author_firstname");
			String authorLastName=rs.getString("author_lastname");
			String resolverFirstName = rs.getString("resolver_firstname");
			String status = rs.getString("status").toUpperCase();
			String type = rs.getString("type").toUpperCase();
			
			reimbursementMap.put(id, new ReimbView(id, amount, submitted, resolved, description, authorFirstname, authorLastName, resolverFirstName, authorLastName, status, type));
		}
		
		return reimbursementMap;
	}
	
	public Map<Integer, ReimbView> selectAllReimbursements(){
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "SELECT * FROM reimb_view";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			
			ResultSet rs = ps.executeQuery();
			
			return mapView(rs);
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, ReimbView> selectReimbursementsByStatus(int statusId){
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "SELECT * FROM reimb_view WHERE status_id = ?";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			ps.setInt(1, statusId);
			
			ResultSet rs = ps.executeQuery();
			
			return mapView(rs);
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Reimbursement> selectReimbursementByAuthorId(int authorId)
	{
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "SELECT * FROM reimbursement WHERE reimb_author_fk = ?";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			ps.setInt(1, authorId);
			
			ResultSet rs = ps.executeQuery();
			
			return mapResult(rs);
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}
		
	public Map<Integer, Reimbursement> selectPastReimbursement(int authorId)
	{
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "SELECT * FROM reimbursement WHERE reimb_resolved IS NOT NULL AND reimb_resolved <= CURRENT_DATE AND reimb_author_fk = ?;";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			ps.setInt(1, authorId);
			
			ResultSet rs = ps.executeQuery();
			
			return mapResult(rs);
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}
	

}