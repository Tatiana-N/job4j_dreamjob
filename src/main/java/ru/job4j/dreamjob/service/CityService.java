package ru.job4j.dreamjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.Store;

import net.jcip.annotations.ThreadSafe;
import java.util.Collection;

@ThreadSafe
@Service
public class CityService implements AppService<City> {
	
	
	private final Store<City> store;
	
	public CityService(@Autowired Store<City> store) {
		this.store = store;
	}
	
	@Override
	public Collection<City> findAll() {
		return store.findAll();
	}
	
	@Override
	public City add(City city) {
		return store.add(city);
	}
	
	@Override
	public City findById(int id) {
		return store.findById(id);
	}
	
	@Override
	public void update(City city) {
		store.update(city);
	}
}
