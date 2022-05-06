package daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.User;

class UserDaoTest {
		UserDao dao = new UserDao();
	
	@BeforeEach
	void setUp() throws Exception {
		dao.initH2database();
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
		dao.destroyH2database();
	}

}
