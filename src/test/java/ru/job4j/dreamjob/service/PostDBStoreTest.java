package ru.job4j.dreamjob.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CityDBStore;
import ru.job4j.dreamjob.store.PostDbStore;
import ru.job4j.dreamjob.store.Store;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

class PostDBStoreTest {
	private static Store<Post> postStore;
	private static Store<City> cityStore;
	
	
	@BeforeEach
	 void before() throws SQLException {
		new Main().loadPool().getConnection().prepareStatement("delete from posts").execute();
		new Main().loadPool().getConnection().prepareStatement("delete from cities").execute();
		postStore = new PostDbStore(new Main().loadPool());
		cityStore = new CityDBStore(new Main().loadPool());
	}
	
	@Test
	void findAll() {
		City city1 = new City(0, "Москва");
		City city2 = new City(0, "Ростов-на-Дону");
		Post post1 = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		Post post2 = new Post(0, "Java Test Very Success", "Awful Description Test", LocalDateTime.now());
		cityStore.add(city1);
		cityStore.add(city2);
		post1.setCity(city1);
		post2.setCity(city2);
		postStore.add(post1);
		postStore.add(post2);
		Collection<Post> all = postStore.findAll();
		Assertions.assertArrayEquals(List.of(post1, post2).toArray(), postStore.findAll().toArray());
		Assertions.assertEquals(postStore.findById(post2.getId()), post2);
		Assertions.assertEquals(2, all.size());
	}
	
	@Test
	public void add() {
		City city1 = new City(0, "Москва");
		Post post1 = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		cityStore.add(city1);
		post1.setCity(city1);
		postStore.add(post1);
		Assertions.assertEquals(postStore.findById(post1.getId()), post1);
	}
	
	@Test
	void findById() {
		City city1 = new City(0, "Москва");
		City city2 = new City(0, "Ростов-на-Дону");
		Post post1 = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		Post post2 = new Post(0, "Java Test Very Success", "Awful Description Test", LocalDateTime.now());
		cityStore.add(city1);
		cityStore.add(city2);
		post1.setCity(city1);
		post2.setCity(city2);
		postStore.add(post1);
		postStore.add(post2);
		Assertions.assertEquals(postStore.findById(post1.getId()), post1);
		Assertions.assertEquals(postStore.findById(post2.getId()), post2);
		Assertions.assertNull(postStore.findById(3));
	}
	
	@Test
	void update() {
		City city1 = new City(0, "Москва");
		City city2 = new City(0, "Ростов-на-Дону");
		Post post = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		Post updatePost = new Post(0, "Java Test Very Success", "Awful Description Test", LocalDateTime.now());
		cityStore.add(city1);
		cityStore.add(city2);
		post.setCity(city1);
		updatePost.setCity(city2);
		postStore.add(post);
		updatePost.setId(post.getId());
		postStore.update(updatePost);
		Assertions.assertEquals(postStore.findById(updatePost.getId()), updatePost);
		Assertions.assertEquals(postStore.findById(post.getId()), updatePost);
		Assertions.assertEquals(1, postStore.findAll().size());
	}
}
