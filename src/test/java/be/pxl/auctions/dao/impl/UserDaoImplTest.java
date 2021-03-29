package be.pxl.auctions.dao.impl;

import be.pxl.auctions.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class UserDaoImplTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserDaoImpl userDao;

	@Test
	public void userCanBeSavedAndRetrievedById() {
		User user = new User();
		user.setFirstName("Mark");
		user.setLastName("Zuckerberg");
		user.setDateOfBirth(LocalDate.of(1989, 5, 3));
		user.setEmail("mark@facebook.com");
		long newUserId = userDao.saveUser(user).getId();
		entityManager.flush();
		entityManager.clear();

		Optional<User> retrievedUser = userDao.findUserById(newUserId);
		assertTrue(retrievedUser.isPresent());

		assertEquals(user.getFirstName(), retrievedUser.get().getFirstName());
		assertEquals(user.getLastName(), retrievedUser.get().getLastName());
		assertEquals(user.getEmail(), retrievedUser.get().getEmail());
		assertEquals(user.getDateOfBirth(), retrievedUser.get().getDateOfBirth());
	}
	@Test
	public void userCanBeSavedAndRetrievedByEmail() {
		// TODO implement this test
		User user = new User();
		user.setFirstName("Robin");
		user.setLastName("Kall");
		user.setEmail("robin.kall@example.com");
		user.setDateOfBirth(LocalDate.of(2000, 10, 6));
		String email = userDao.saveUser(user).getEmail();
		entityManager.flush();
		entityManager.clear();
		Optional<User> retrievedUser = userDao.findUserByEmail(email);
		assertTrue(retrievedUser.isPresent());
		assertEquals(user.getFirstName(), retrievedUser.get().getFirstName());
		assertEquals(user.getLastName(), retrievedUser.get().getLastName());
		assertEquals(user.getEmail(), retrievedUser.get().getEmail());
		assertEquals(user.getDateOfBirth(), retrievedUser.get().getDateOfBirth());	

	}

	@Test
	public void returnsNullWhenNoUserFoundWithGivenEmail() {
		User user = new User();
		user.setFirstName("Robin");
		user.setLastName("Kall");
		user.setEmail("robin.kall@example.com");
		user.setDateOfBirth(LocalDate.of(2000, 10, 6));
		entityManager.flush();
		entityManager.clear();
		Optional<User> retrievedUser = userDao.findUserByEmail("not.existing@email.example");
		assertFalse(retrievedUser.isPresent());
	}

	@Test
	public void allUsersCanBeRetrieved() {
		// create and save one user
		User user = new User();
		user.setFirstName("Robin");
		user.setLastName("Kall");
		user.setEmail("robin.kall@example.com");
		user.setDateOfBirth(LocalDate.of(2000, 10, 6));
		String email = userDao.saveUser(user).getEmail();
		entityManager.flush();
		entityManager.clear();
		// retrieve all users
		ArrayList<User> users = new ArrayList<User>();
		users.addAll(userDao.findAllUsers());
		// make sure there is at least 1 user in the list
		assertTrue(users.size() >= 1);
		// make sure the newly created user is in the list (e.g. test if a user with this email address is in the list)
		long usersWithCreatedEmail = users.stream().filter(u -> u.getEmail().equals(email)).count();
		assertEquals(1, (int)usersWithCreatedEmail);
	}


}
