package ru.job4j.dreamjob.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "created")
public class Post {
	private int id;
	private String name;
	private String description;
	private LocalDateTime created;
	private boolean visible;
	private City city;
	
	public Post(int id, String name, String description, LocalDateTime created) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
	}
}
