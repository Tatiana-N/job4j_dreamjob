package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidatesStore {
	
	private static final CandidatesStore INST = new CandidatesStore();
	
	private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
	
	private CandidatesStore() {
		candidates.put(1, new Candidate(1, "VAnya", "good", LocalDate.of(2000, 6, 15)));
		candidates.put(2, new Candidate(2, "TAnya", "bad", LocalDate.of(2001, 9, 13)));
		candidates.put(3, new Candidate(3, "SAnya", "good", LocalDate.of(2022, 9, 30)));
	}
	
	public static CandidatesStore instOf() {
		return INST;
	}
	
	public Collection<Candidate> findAll() {
		return candidates.values();
	}
}
