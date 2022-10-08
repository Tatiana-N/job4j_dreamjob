package ru.job4j.dreamjob.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Post {
	private int id;
	private String name;
	private String description;
	private LocalDate created;
	private boolean visible;
	
	public Post() {
	}
	
	public Post(int id, String name, String description, LocalDate created) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
	}
}
