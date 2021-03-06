package ru.job4j.dream.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Candidate {
	private int id;
	private String name;
	
	public Candidate(int id, String name) {
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
		Candidate candidate = (Candidate) o;
		return id == candidate.id && Objects.equals(name, candidate.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
