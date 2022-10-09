package ru.job4j.dreamjob.service;

import java.util.Collection;
import net.jcip.annotations.ThreadSafe;


@ThreadSafe
public interface AppService<T> {
	Collection<T> findAll();
	
	T add(T t);
	
	T findById(int id);
	
	void update(T t);
}
