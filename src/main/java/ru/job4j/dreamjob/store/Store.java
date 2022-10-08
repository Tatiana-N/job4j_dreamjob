package ru.job4j.dreamjob.store;

import java.util.Collection;

public interface Store<T> {
	
	T findById(int id);
	
	Collection<T> findAll();
	
	T add(T t);
	
	T update(T t);
}
