package ru.job4j.dreamjob.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.store.CandidatesStore;
import ru.job4j.dreamjob.store.PostStore;

@Controller
public class CandidateController {
	
	private final CandidatesStore store = CandidatesStore.instOf();
	
	@GetMapping("/candidate")
	public String posts(Model model) {
		model.addAttribute("candidates", store.findAll());
		return "candidates";
	}
}
