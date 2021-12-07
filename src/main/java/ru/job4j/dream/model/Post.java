package ru.job4j.dream.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
public class Post {
	private final int id;
	private final String name;
	private String description;
	private Calendar created;
	
	
	public Post(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Post post = (Post) o;
		return id == post.id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

