package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;

public interface Store {
	Collection<Post> findAllPosts();
	
	Collection<Candidate> findAllCandidates();
	
	String save(Post post);
	
	String save(Candidate candidate);
	
	Post findByIdPost(Integer id);
	
	Candidate findByIdCandidate(Integer parseInt);
	
	void deletePost(Integer id);
	
	void deleteCandidate(Integer id);
}
