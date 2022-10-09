package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
//@Repository
public class PostStore implements Store<Post> {
	
	private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
	private final AtomicInteger id;
	
	private PostStore() {
		Post post1 = new Post(1, "Junior Java Job", "сопляк", LocalDateTime.now());
		post1.setCity(new City(1, "Москва1"));
		Post post2 = new Post(1, "Middle Java Job", "нормальный такой", LocalDateTime.now());
		post2.setCity(new City(1, "Москва2"));
		Post post3 = new Post(1, "Senior Java Job", "сопляк", LocalDateTime.now());
		post3.setCity(new City(1, "Москва3"));
		posts.put(1, post1);
		posts.put(2, post2);
		posts.put(3, post3);
		id = new AtomicInteger(posts.size());
	}
	
	public Collection<Post> findAll() {
		return posts.values();
	}
	
	public Post findById(int id) {
		return posts.get(id);
	}
	
	public Post add(Post post) {
		int id = this.id.incrementAndGet();
		post.setCreated(LocalDateTime.now());
		post.setId(id);
		posts.put(id, post);
		return post;
	}
	
	public Post update(Post post) {
		post.setCreated(LocalDateTime.now());
		return posts.replace(post.getId(), post);
	}
}
