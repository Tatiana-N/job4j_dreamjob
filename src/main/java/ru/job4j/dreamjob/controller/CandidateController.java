package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CandidatesStore;
import ru.job4j.dreamjob.store.PostStore;

import java.time.LocalDate;

@Controller
public class CandidateController {
	
	private final CandidatesStore store = CandidatesStore.instOf();
	
	@GetMapping("/candidates")
	public String candidates(Model model) {
		model.addAttribute("candidates", store.findAll());
		return "candidates";
	}
	
	@GetMapping("/formAddCandidate")
	public String addCandidate(Model model) {
		model.addAttribute("candidate", new Candidate());
		return "addCandidate";
	}
	
	@PostMapping("/createCandidate")
	public String createCandidate(@ModelAttribute Candidate candidate) {
		candidate.setCreated(LocalDate.now());
		store.add(candidate);
		return "redirect:/candidates";
	}
	
	@PostMapping("/updateCandidate")
	public String updateCandidate(@ModelAttribute Candidate candidate) {
		store.updateCandidate(candidate);
		return "redirect:/candidates";
	}
	
	@GetMapping("/formUpdateCandidate/{candidateId}")
	public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
		model.addAttribute("candidate", store.findById(id));
		return "updateCandidate";
	}
}
