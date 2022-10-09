package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CityStore implements Store<City> {
	
	private final Map<Integer, City> cities = new ConcurrentHashMap<>();
	private final AtomicInteger id;
	
	private CityStore() {
		cities.put(1, new City(1, "Moscow"));
		cities.put(2, new City(2, "Rostov-on-Don"));
		cities.put(3, new City(3, "Eisk"));
		id = new AtomicInteger(cities.size());
	}
	
	public Collection<City> findAll() {
		return cities.values();
	}
	
	public City findById(int id) {
		return cities.get(id);
	}
	
	public City add(City city) {
		int id = this.id.incrementAndGet();
		city.setId(id);
		cities.put(id, city);
		return city;
	}
	
	public City update(City city) {
		return cities.replace(city.getId(), city);
	}
}
