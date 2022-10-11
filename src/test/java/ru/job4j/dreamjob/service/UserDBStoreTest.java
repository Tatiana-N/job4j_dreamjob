package ru.job4j.dreamjob.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDbStore;
import ru.job4j.dreamjob.store.Store;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

class UserDBStoreTest {
	private static UserDbStore userStore;
	
	
	@BeforeEach
	void before() throws SQLException {
		new Main().loadPool().getConnection().prepareStatement("delete from users").execute();
		userStore = new UserDbStore(new Main().loadPool());
	}
	
	@Test
	void findAll() {
		User user1 = new User(0, "User Name Test", "JavaTestSuccess@mail.ru", "password");
		User user2 = new User(0, "UserTwo Name Test", "JavaTestVerySuccess@mail.ru", "password");
		userStore.add(user1);
		userStore.add(user2);
		Collection<User> all = userStore.findAll();
		Assertions.assertArrayEquals(List.of(user1, user2).toArray(), userStore.findAll().toArray());
		Assertions.assertEquals(userStore.findById(user2.getId()), user2);
		Assertions.assertEquals(2, all.size());
	}
	
	@Test
	public void add() {
		User user = new User(0, "User Name Test", "JavaTestSuccess@mail.ru", "password");
		userStore.add(user);
		Assertions.assertEquals(userStore.findById(user.getId()), user);
		User userNotRegistration = new User(0, "User Name Not Registration", "JavaTestSuccess@mail.ru", "password");
		Assertions.assertTrue(userStore.add(userNotRegistration).isEmpty());
		Assertions.assertEquals(1, userStore.findAll().size());
		Assertions.assertTrue(userStore.findAll().contains(user));
	}
	
	@Test
	void findById() {
		User user1 = new User(0, "User Name Test", "JavaTestSuccess@mail.ru", "password");
		User user2 = new User(0, "UserTwo Name Test", "JavaTestVerySuccess@mail.ru", "password");
		userStore.add(user1);
		userStore.add(user2);
		Assertions.assertEquals(userStore.findById(user1.getId()), user1);
		Assertions.assertEquals(userStore.findById(user2.getId()), user2);
		Assertions.assertNull(userStore.findById(3));
	}
	
	@Test
	void update() {
		User user = new User(0, "User Name Test", "JavaTestSuccess@mail.ru", "password");
		User updateUser = new User(0, "UserTwo Name Test", "JavaTestVerySuccess@mail.ru", "password");
		userStore.add(user);
		updateUser.setId(user.getId());
		userStore.update(updateUser);
		Assertions.assertEquals(userStore.findById(updateUser.getId()), updateUser);
		Assertions.assertEquals(userStore.findById(user.getId()), updateUser);
		Assertions.assertEquals(1, userStore.findAll().size());
	}
}
