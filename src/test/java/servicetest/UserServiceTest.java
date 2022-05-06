package servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import models.User;
import service.UserService;

class UserServiceTest {
	
	private UserDao mockDao;
	private UserService service;
	
	@BeforeEach
	void setUp() {
		mockDao = mock(UserDao.class);
		service = new UserService(mockDao);
	}
	
	@Test
	void loginServiceTest() {
		//ARRANGE
		User user = new User();
		user.setUsername("asdfgh");
		user.setPassword("asdfgh");
		
		when(mockDao.selectUser("asdfgh")).thenReturn(user);
		
		//ACT
		User UserOne = service.validateLogin("asdfgh", "asdfgh");
		User userTwo = service.validateLogin("asdfgh", "qwerty");
		
		//ASSERT
		assertEquals(user, UserOne);
		assertNull(userTwo);
		verify(mockDao,times(2)).selectUser("asdfgh");
	}
	
	@Test
	void getAllFinanceManagersTest() {
		//Arrange
		User fmOne = new User();
		User fmTwo = new User();
		
		List<User> financeManagers = new ArrayList<>();
		financeManagers.add(fmOne);
		financeManagers.add(fmTwo);
		
		List<User> expectedList = new ArrayList<>();
		expectedList.addAll(financeManagers);
		
		when(mockDao.selectAllFinanceManagers()).thenReturn(financeManagers);
		
		//ACT
		List<User> actualList = service.getAllFinanceMangers();
		
		//Assert
		assertEquals(expectedList, actualList);
		verify(mockDao, times(1)).selectAllFinanceManagers();
	}

}
