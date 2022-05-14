package models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reimbursement {	
	int Id;
	double amount;
	Date submitted;
	Date resolved;
	String description;
	int authorId;
	int resolverId;
	int statusId;
	int typeId;
	
//	public Reimbursement(double amount, String description, int authorId, int resolverId, int statusId, int typeId) {
//		this.amount = amount;
//		this.description = description;
//		this.authorId = authorId;
//		this.resolverId = resolverId;
//		this.statusId = statusId;
//		this.typeId = typeId;
//	}	
}
