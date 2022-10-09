package ru.job4j.dreamjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.AppService;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
@Controller
public class PostController {
	
	private final AppService<Post> postService;
	private final AppService<City> cityService;
	
	public PostController(@Autowired AppService<Post> postService, AppService<City> cityService) {
		this.postService = postService;
		this.cityService = cityService;
	}
	
	@GetMapping("/posts")
	public String posts(Model model) {
		model.addAttribute("posts", postService.findAll());
		return "posts";
	}
	
	@GetMapping("/formAddPost")
	public String addPost(Model model) {
		model.addAttribute("cities", cityService.findAll());
		return "addPost";
	}
	
	@PostMapping("/createPost")
	public String createPost(@ModelAttribute Post post) {
		post.setCity(cityService.findById(post.getCity().getId()));
		postService.add(post);
		return "redirect:/posts";
	}
	
	
	@GetMapping("/formUpdatePost/{postId}")
	public String formUpdatePost(@PathVariable("postId") int postId, Model model) {
		model.addAttribute("post", postService.findById(postId));
		model.addAttribute("cities", cityService.findAll());
		return "updatePost";
	}
	
	@PostMapping("/updatePost")
	public String updatePost(@ModelAttribute Post post) {
		post.setCity(cityService.findById(post.getCity().getId()));
		postService.update(post);
		return "redirect:/posts";
	}
}
