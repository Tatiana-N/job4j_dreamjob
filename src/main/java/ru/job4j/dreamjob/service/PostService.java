package ru.job4j.dreamjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.Store;

import java.util.Collection;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Service
public class PostService implements AppService<Post> {
	
	
	private final Store<Post> store;
	
	public PostService(@Autowired Store<Post> store) {
		this.store = store;
	}
	
	@Override
	public Collection<Post> findAll() {
		return store.findAll();
	}
	
	@Override
	public Post add(Post post) {
		return store.add(post);
	}
	
	@Override
	public Post findById(int id) {
		return store.findById(id);
	}
	
	@Override
	public void update(Post post) {
		store.update(post);
	}
}
