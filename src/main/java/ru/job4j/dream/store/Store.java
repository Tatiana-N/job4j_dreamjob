package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
	Collection<Post> findAllPosts();
	
	Collection<Candidate> findAllCandidates();
	
	Collection<User> findAllUsers();
	
	String save(Post post);
	
	String save(User user);
	
	String save(Candidate candidate);
	
	Post findByIdPost(Integer id);
	
	User findByEmail(String email);
	
	Candidate findByIdCandidate(Integer parseInt);
	
	void deletePost(Integer id);
	
	void deleteCandidate(Integer id);
	
	void deleteUser(Integer id);
}
