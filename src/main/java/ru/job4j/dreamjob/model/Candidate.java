package ru.job4j.dreamjob.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Candidate {
	private int id;
	private String name;
	private String description;
	private LocalDateTime created;
	private byte[] photo;
	
	public Candidate(int id, String name, String description, LocalDateTime created) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
	}
}
