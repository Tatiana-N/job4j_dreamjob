package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.Store;
import ru.job4j.dreamjob.store.UserDbStore;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class UserService implements AppService<User> {
	
	private final Store<User> store;
	
	public UserService(@Autowired Store<User> store) {
		this.store = store;
	}
	
	@Override
	public Collection<User> findAll() {
		return store.findAll();
	}
	
	@Override
	public User add(User user) {
		return store.add(user);
	}
	
	@Override
	public User findById(int id) {
		return store.findById(id);
	}
	
	@Override
	public void update(User user) {
		store.update(user);
	}
	
	public Optional<User> findUserByEmailAndPwd(String email, String password) {
		return ((UserDbStore) store).findUserByEmailAndPwd(email,  password);
	}
	
	public Optional<User> registration(User user) {
		return ((UserDbStore) store).registration(user);
	}
}
