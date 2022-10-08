package ru.job4j.dreamjob.service;

import java.util.Collection;

public interface Service<T> {
	Collection<T> findAll();
	
	T add(T t);
	
	T findById(int id);
	
	void update(T t);
}
