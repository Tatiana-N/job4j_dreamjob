package ru.job4j.dreamjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.Service;

import java.time.LocalDate;

@Controller
public class PostController {
	@Autowired
	Service<Post> postService;
	
	@GetMapping("/posts")
	public String posts(Model model) {
		model.addAttribute("posts", postService.findAll());
		return "posts";
	}
	
	@GetMapping("/formAddPost")
	public String addPost(Model model) {
		return "addPost";
	}
	
	@PostMapping("/createPost")
	public String createPost(@ModelAttribute Post post) {
		post.setCreated(LocalDate.now());
		postService.add(post);
		return "redirect:/posts";
	}
	
	
	@GetMapping("/formUpdatePost/{postId}")
	public String formUpdatePost(@PathVariable("postId") int postId, Model model) {
		model.addAttribute("post", postService.findById(postId));
		return "updatePost";
	}
	
	@PostMapping("/updatePost")
	public String updatePost(@ModelAttribute Post post) {
		postService.update(post);
		return "redirect:/posts";
	}
}
