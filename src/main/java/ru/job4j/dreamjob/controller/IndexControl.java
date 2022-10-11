package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.http.HttpSession;

import static ru.job4j.dreamjob.util.Util.getUser;

/**
 * @RestController (является сочетанием аннотаций @ Controller и @ ResponseBody) на
 * @Controller (обозначает что данный класс является контроллером в модели Spring MVC).
 */
@ThreadSafe
@Controller
public class IndexControl {
	
	@GetMapping("/index")
	public String index(Model model, HttpSession session) {
		model.addAttribute("user", getUser(session));
		return "index";
	}
}
