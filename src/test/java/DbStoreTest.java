import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DbStoreTest {
	
	@BeforeEach
	public void before() {
		Store store = DbStore.instOf();
		Collection<Candidate> allCandidates = store.findAllCandidates();
		Collection<Post> allPosts = store.findAllPosts();
		allCandidates.forEach(candidate -> store.deleteCandidate(candidate.getId()));
		allPosts.forEach(post -> store.deletePost(post.getId()));
	}
	
	@Test
	public void whenCreatePost() {
		Store store = DbStore.instOf();
		Post post = new Post(0, "Java Job");
		store.save(post);
		Post postInDb = store.findByIdPost(post.getId());
		Assertions.assertEquals(postInDb.getName(), post.getName());
	}
	
	@Test
	public void whenCreateCandidate() {
		Store store = DbStore.instOf();
		Candidate candidate = new Candidate(0, "Java Job");
		store.save(candidate);
		Candidate candidateInDb = store.findByIdCandidate(candidate.getId());
		Assertions.assertEquals(candidateInDb.getName(), candidate.getName());
	}
	
	@Test
	public void whenUpdateCandidate() {
		Store store = DbStore.instOf();
		Candidate candidate1 = new Candidate(0, "Java Job");
		String id1 = store.save(candidate1);
		Candidate candidate2 = new Candidate(Integer.parseInt(id1), "Java Job1");
		String id2 = store.save(candidate2);
		Assertions.assertEquals(id1, id2);
		Candidate candidateInDb = store.findByIdCandidate(candidate1.getId());
		Assertions.assertEquals(candidateInDb.getName(), candidate2.getName());
	}
	
	@Test
	public void whenUpdatePost() {
		Store store = DbStore.instOf();
		Post post1 = new Post(0, "Java Job");
		String id1 = store.save(post1);
		Post post2 = new Post(Integer.parseInt(id1), "Java Job1");
		String id2 = store.save(post2);
		Assertions.assertEquals(id1, id2);
		Post candidateInDb = store.findByIdPost(post1.getId());
		Assertions.assertEquals(candidateInDb.getName(), post2.getName());
	}
	
	@Test
	public void whenDeletePost() {
		Store store = DbStore.instOf();
		Post post = new Post(0, "Java Job");
		String id = store.save(post);
		store.deletePost(Integer.parseInt(id));
		Post postInDb = store.findByIdPost(post.getId());
		Assertions.assertNull(postInDb);
	}
	
	@Test
	public void whenDeleteCandidate() {
		Store store = DbStore.instOf();
		Candidate candidate = new Candidate(0, "Java Job");
		String id = store.save(candidate);
		store.deletePost(Integer.parseInt(id));
		Post postInDb = store.findByIdPost(candidate.getId());
		Assertions.assertNull(postInDb);
	}
	
	@Test
	public void whenFindAllCandidates() {
		Store store = DbStore.instOf();
		Candidate candidate1 = new Candidate(0, "Java Candidate1");
		Candidate candidate2 = new Candidate(0, "Java Candidate2");
		Candidate candidate3 = new Candidate(0, "Java Candidate3");
		int id1 = Integer.parseInt(store.save(candidate1));
		int id2 = Integer.parseInt(store.save(candidate2));
		int id3 = Integer.parseInt(store.save(candidate3));
		candidate1.setId(id1);
		candidate2.setId(id2);
		candidate3.setId(id3);
		Candidate candidate4 = new Candidate(id1, "Java Candidate4");
		int id4 = Integer.parseInt(store.save(candidate4));
		candidate4.setId(id4);
		List<Candidate> candidateList = Arrays.asList(candidate4, candidate2, candidate3);
		Assertions.assertEquals(store.findAllCandidates(), candidateList);
	}
	
	@Test
	public void whenFindAllPosts() {
		Store store = DbStore.instOf();
		Post post1 = new Post(0, "Java Job1");
		Post post2 = new Post(0, "Java Job2");
		Post post3 = new Post(0, "Java Job3");
		int id1 = Integer.parseInt(store.save(post1));
		store.save(post2);
		store.save(post3);
		int id2 = Integer.parseInt(store.save(post2));
		post1.setId(id1);
		post2.setId(id2);
		int id3 = Integer.parseInt(store.save(post3));
		Post post4 = new Post(id1, "Java Job4");
		store.save(post4);
		post3.setId(id3);
		List<Post> posts = Arrays.asList(post4, post2, post3);
		Assertions.assertEquals(store.findAllPosts(), posts);
	}
}
