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
public class UserService {
	
	private final UserDbStore store;
	
	public UserService(@Autowired UserDbStore store) {
		this.store = store;
	}
	
	public Collection<User> findAll() {
		return store.findAll();
	}
	
	public Optional<User> add(User user) {
		return store.add(user);
	}
	
	public User findById(int id) {
		return store.findById(id);
	}
	
	public void update(User user) {
		store.update(user);
	}
	
	public Optional<User> findUserByEmailAndPwd(String email, String password) {
		return store.findUserByEmailAndPwd(email,  password);
	}
}
