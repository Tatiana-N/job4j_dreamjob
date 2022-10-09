package ru.job4j.dreamjob.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;
import ru.job4j.dreamjob.store.Store;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

class CandidateDBStoreTest {
	private static Store<Candidate> candidateStore;
	
	
	@BeforeEach
	void before() throws SQLException {
		new Main().loadPool().getConnection().prepareStatement("delete from candidates").execute();
		candidateStore = new CandidateDbStore(new Main().loadPool());
	}
	
	@Test
	void findAll() {
		Candidate candidate1 = new Candidate(0, "Java Test Success", "Description Test", LocalDateTime.now());
		Candidate candidate2 = new Candidate(0, "Java Test Very Success", "Awful Description Test", LocalDateTime.now());
		candidateStore.add(candidate1);
		candidateStore.add(candidate2);
		Collection<Candidate> all = candidateStore.findAll();
		Assertions.assertArrayEquals(List.of(candidate1, candidate2).toArray(), candidateStore.findAll().toArray());
		Assertions.assertEquals(candidateStore.findById(candidate2.getId()), candidate2);
		Assertions.assertEquals(2, all.size());
	}
	
	@Test
	public void add() {
		Candidate candidate = new Candidate(0, "Java Test Success", "Description Test", LocalDateTime.now());
		candidateStore.add(candidate);
		Assertions.assertEquals(candidateStore.findById(candidate.getId()), candidate);
	}
	
	@Test
	void findById() {
		Candidate candidate1 = new Candidate(0, "Java Test Success", "Description Test", LocalDateTime.now());
		Candidate candidate2 = new Candidate(0, "Java Test Very Success", "Awful Description Test", LocalDateTime.now());
		candidateStore.add(candidate1);
		candidateStore.add(candidate2);
		Assertions.assertEquals(candidateStore.findById(candidate1.getId()), candidate1);
		Assertions.assertEquals(candidateStore.findById(candidate2.getId()), candidate2);
		Assertions.assertNull(candidateStore.findById(3));
	}
	
	@Test
	void update() {
		Candidate candidate = new Candidate(0, "Java Test Success", "Description Test", LocalDateTime.now());
		Candidate updateCandidate = new Candidate(0, "Java Test Very Success", "Awful Description Test", LocalDateTime.now());
		candidateStore.add(candidate);
		updateCandidate.setId(candidate.getId());
		candidateStore.update(updateCandidate);
		Assertions.assertEquals(candidateStore.findById(updateCandidate.getId()), updateCandidate);
		Assertions.assertEquals(candidateStore.findById(candidate.getId()), updateCandidate);
		Assertions.assertEquals(1, candidateStore.findAll().size());
	}
}
