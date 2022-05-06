package dao;

import static factory.ConnectionFactory.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;


public class UserDao {
	
	public User selectUser(String username) {
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "SELECT * FROM ers_users WHERE user_name = ?";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String password = rs.getString("user_password");
				String firstName = rs.getString("user_firstName");
				String lastName = rs.getString("user_lastName");
				String email = rs.getString("user_email");
				int roleId = rs.getInt("user_role_fk");

				return new User(userId, username, password, firstName, lastName, email, roleId);
			} else
				return null;

		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}
			
	public List<User> selectAllFinanceManagers(){
		try (Connection conn = getConnection()) {
			String ourSQLStatement = "SELECT user_id, user_firstName, user_lastName FROM ers_users WHERE user_role_fk=1";

			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);

			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				List<User> f = new ArrayList<User>();
				do {
					int userId = rs.getInt("user_id");
					String firstName = rs.getString("user_firstName");
					String lastName = rs.getString("user_lastName");
					
					User user = new User();
					user.setUserId(userId);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					
					f.add(user);
				}while(rs.next());
				
				return f;
			} else
				return null;

		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}	
}
