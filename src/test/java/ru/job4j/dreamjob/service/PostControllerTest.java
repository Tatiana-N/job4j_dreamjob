package ru.job4j.dreamjob.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.controller.PostController;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class PostControllerTest {
	
	@Test
	public void posts() {
		HttpSession httpSession = mock(HttpSession.class);
		PostService postService = mock(PostService.class);
		CityService cityService = mock(CityService.class);
		Model model = mock(Model.class);
		when(httpSession.getAttribute("user")).thenReturn(null);
		PostController postController = new PostController(postService, cityService);
		User user = new User();
		user.setName("Гость");
		Post post1 = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		Post post2 = new Post(0, "Java Test Very Success", "Awful Description Test", LocalDateTime.now());
		List<Post> posts = Arrays.asList(post1, post2);
		when(postService.findAll()).thenReturn(posts);
		String page = postController.posts(model, httpSession);
		verify(postService).findAll();
		verify(model).addAttribute("posts", posts);
		verify(model).addAttribute("user", user);
		Assertions.assertEquals(page, "posts");
	}
	
	@Test
	public void createPost() {
		HttpSession httpSession = mock(HttpSession.class);
		PostService postService = mock(PostService.class);
		CityService cityService = mock(CityService.class);
		when(httpSession.getAttribute("user")).thenReturn(null);
		PostController postController = new PostController(postService, cityService);
		City city1 = new City(0, "Москва");
		Post post1 = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		post1.setCity(city1);
		when(cityService.findById(0)).thenReturn(city1);
		String page = postController.createPost(post1);
		verify(cityService).findById(0);
		verify(postService).add(post1);
		Assertions.assertEquals("redirect:/posts", page);
	}
	
	@Test
	public void formAddPost() {
		HttpSession httpSession = mock(HttpSession.class);
		PostService postService = mock(PostService.class);
		CityService cityService = mock(CityService.class);
		Model model = mock(Model.class);
		when(httpSession.getAttribute("user")).thenReturn(null);
		PostController postController = new PostController(postService, cityService);
		User user = new User();
		user.setName("Гость");
		City city1 = new City(0, "Москва");
		City city2 = new City(0, "Ростов-на-Дону");
		List<City> cities = Arrays.asList(city1, city2);
		when(cityService.findAll()).thenReturn(cities);
		String page = postController.addPost(model, httpSession);
		verify(cityService).findAll();
		verify(model).addAttribute("cities", cities);
		verify(model).addAttribute("user", user);
		Assertions.assertEquals(page, "addPost");
	}
	
	@Test
	public void formUpdatePostOne() {
		HttpSession httpSession = mock(HttpSession.class);
		PostService postService = mock(PostService.class);
		CityService cityService = mock(CityService.class);
		Model model = mock(Model.class);
		when(httpSession.getAttribute("user")).thenReturn(null);
		PostController postController = new PostController(postService, cityService);
		User user = new User();
		user.setName("Гость");
		City city1 = new City(0, "Москва");
		City city2 = new City(0, "Ростов-на-Дону");
		Post post = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		post.setCity(city1);
		List<City> cities = Arrays.asList(city1, city2);
		when(cityService.findAll()).thenReturn(cities);
		when(postService.findById(1)).thenReturn(post);
		String page = postController.formUpdatePost(1, model, httpSession);
		verify(postService).findById(1);
		verify(cityService).findAll();
		verify(model).addAttribute("post", post);
		verify(model).addAttribute("cities", cities);
		verify(model).addAttribute("user", user);
		Assertions.assertEquals(page, "updatePost");
	}
	
	@Test
	public void updatePost() {
		HttpSession httpSession = mock(HttpSession.class);
		PostService postService = mock(PostService.class);
		CityService cityService = mock(CityService.class);
		when(httpSession.getAttribute("user")).thenReturn(null);
		PostController postController = new PostController(postService, cityService);
		City city = new City(0, "Москва");
		Post post = new Post(0, "Java Test Success", "Description Test", LocalDateTime.now());
		post.setCity(city);
		when(cityService.findById(0)).thenReturn(city);
		String page = postController.updatePost(post);
		verify(postService).update(post);
		Assertions.assertEquals(page, "redirect:/posts");
	}
}
	