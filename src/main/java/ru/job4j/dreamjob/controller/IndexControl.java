package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.jcip.annotations.ThreadSafe;

/**
 * @RestController (является сочетанием аннотаций @ Controller и @ ResponseBody) на
 * @Controller (обозначает что данный класс является котроллером в модели Spring MVC).
 */
@ThreadSafe
@Controller
public class IndexControl {
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
