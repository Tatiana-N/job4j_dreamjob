package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Repository
public class CandidatesStore implements Store<Candidate> {
	
	private final AtomicInteger id;
	private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
	
	private CandidatesStore() {
		candidates.put(1, new Candidate(1, "VAnya", "good", LocalDateTime.now()));
		candidates.put(2, new Candidate(2, "TAnya", "bad", LocalDateTime.now()));
		candidates.put(3, new Candidate(3, "SAnya", "good", LocalDateTime.now()));
		id = new AtomicInteger(candidates.size());
	}
	
	public Collection<Candidate> findAll() {
		return candidates.values();
	}
	
	public Candidate findById(int id) {
		return candidates.get(id);
	}
	
	public Candidate update(Candidate candidate) {
		candidate.setCreated(LocalDateTime.now());
		return candidates.replace(candidate.getId(), candidate);
	}
	
	public Candidate add(Candidate candidate) {
		int id = this.id.incrementAndGet();
		candidate.setId(id);
		candidate.setCreated(LocalDateTime.now());
		candidates.put(id, candidate);
		return candidate;
	}
}
