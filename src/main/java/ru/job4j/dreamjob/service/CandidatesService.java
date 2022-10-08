package ru.job4j.dreamjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.Store;

import java.util.Collection;

@org.springframework.stereotype.Service
public class CandidatesService implements Service<Candidate> {
	
	private final Store<Candidate> store;
	
	public CandidatesService(@Autowired Store<Candidate> store) {
		this.store = store;
	}
	
	@Override
	public Collection<Candidate> findAll() {
		return store.findAll();
	}
	
	@Override
	public Candidate add(Candidate candidate) {
		return store.add(candidate);
	}
	
	@Override
	public Candidate findById(int id) {
		return store.findById(id);
	}
	
	@Override
	public void update(Candidate candidate) {
		store.update(candidate);
	}
}
