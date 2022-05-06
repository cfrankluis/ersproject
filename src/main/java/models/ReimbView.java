package models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReimbView {
	int Id;
	double amount;
	Date submitted;
	Date resolved;
	String description;
	String authorFirstName;
	String authorLastName;
	String resolverFirstName;
	String resolverLastName;
	String status;
	String type;
}
