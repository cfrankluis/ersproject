package daotest;

import static factory.ConnectionFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import factory.ConnectionFactory;
import models.User;
import dao.UserDao;

class UserDaoTest {
		UserDao dao = new UserDao();
		
	@BeforeEach
	void setUp() throws Exception {
		ConnectionFactory.setTest(true);
		File scriptFile = new File("src\\test\\resources\\CreateScript");
		Scanner sc = new Scanner(scriptFile);
		String sqlStatement = "";
		while(sc.hasNext()) {
			sqlStatement += sc.nextLine();
		}
		
		try(Connection conn = getTestConnection()) {
			Statement state = conn.createStatement();
			state.execute(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void selectUserTest() {
		//ARRANGE
		String username = "jhndoe";
		User expectedUser = 
				new User(
						1
						, "jhndoe"
						, "shellydekiller"
						, "John"
						, "Doe"
						, "dekiller@shelly.com"
						, 1);
		
		//ACT
		User user = dao.selectUser(username);
		
		//ASSERT
		assertEquals(user, expectedUser);
	}
	
	@Test
	void selectAllFinanceManagersTest() {
		//ARRANGE
		User UserOne = 
				new User(
						1
						, null
						, null
						, "John"
						, "Doe"
						, null
						, 0);
		
		User UserTwo = 
				new User(
						2
						, null
						, null
						, "Jane"
						, "Doe"
						, null
						, 0);
		
		User UserThree = 
				new User(
						3
						, null
						, null
						, "Danny"
						, "Fenton"
						, null
						, 0);
		
		List<User> expectedManagers = new ArrayList<>();
		expectedManagers.add(UserOne);
		expectedManagers.add(UserTwo);
		expectedManagers.add(UserThree);
		
		//ACT
		List<User> actualManagers = dao.selectAllFinanceManagers();
		//ASSERT
		assertEquals(expectedManagers,actualManagers);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		File scriptFile = new File("src\\test\\resources\\DropScript");
		Scanner sc = new Scanner(scriptFile);
		String sqlStatement = "";
		while(sc.hasNext()) {
			sqlStatement += sc.nextLine();
		}
		
		try(Connection conn = getTestConnection()) {
			Statement state = conn.createStatement();
			state.execute(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.setTest(false);
	}

}
