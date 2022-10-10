package ru.job4j.dreamjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.AppService;

import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.time.LocalDateTime;

@ThreadSafe
@Controller
public class CandidateController {
	
	private final AppService<Candidate> service;
	
	public CandidateController(@Autowired AppService<Candidate> service) {
		this.service = service;
	}
	
	@GetMapping("/candidates")
	public String candidates(Model model) {
		model.addAttribute("candidates", service.findAll());
		return "candidates";
	}
	
	@GetMapping("/formAddCandidate")
	public String addCandidate(Model model) {
		model.addAttribute("candidate", new Candidate());
		return "addCandidate";
	}
	
	@PostMapping("/createCandidate")
	public String createCandidate(@ModelAttribute Candidate candidate, @RequestParam("file") MultipartFile file) throws IOException {
		candidate.setPhoto(file.getBytes());
		candidate.setCreated(LocalDateTime.now());
		service.add(candidate);
		return "redirect:/candidates";
	}
	
	@PostMapping("/updateCandidate")
	public String updateCandidate(@ModelAttribute Candidate candidate, @RequestParam("file") MultipartFile file) throws IOException {
		candidate.setPhoto(file.getBytes());
		candidate.setCreated(LocalDateTime.now());
		service.update(candidate);
		return "redirect:/candidates";
	}
	
	@GetMapping("/formUpdateCandidate/{candidateId}")
	public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
		model.addAttribute("candidate", service.findById(id));
		return "updateCandidate";
	}
	
	@GetMapping("/photoCandidate/{candidateId}")
	public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
		Candidate candidate = service.findById(candidateId);
		return ResponseEntity
				.ok()
				.headers(new HttpHeaders())
				.contentLength(candidate.getPhoto().length)
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new ByteArrayResource(candidate.getPhoto()));
	}
}
