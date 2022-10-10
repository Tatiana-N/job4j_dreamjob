package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.AppService;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {
	
	private final AppService<User> service;
	
	public UserController(@Autowired AppService<User> service) {
		this.service = service;
	}
	
	@PostMapping("/registration")
	public String registration(Model model, @ModelAttribute User user) {
		Optional<User> regUser = ((UserService) service).registration(user);
		if (regUser.isEmpty()) {
			model.addAttribute("message", "Пользователь с такой почтой уже существует");
			return "redirect:/fail";
		}
		return "redirect:/success";
	}
}
