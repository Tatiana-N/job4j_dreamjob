package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;
import ru.job4j.dreamjob.store.Store;

import java.util.Collection;

@org.springframework.stereotype.Service
public class PostService implements Service<Post> {
	
	private final Store<Post> store = PostStore.instOf();
	
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
