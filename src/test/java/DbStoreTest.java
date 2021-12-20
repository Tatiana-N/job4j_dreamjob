import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

public class DbStoreTest {
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
		String id = store.save(candidate1);
		store.save(candidate2);
		store.save(candidate3);
		Candidate candidate4 = new Candidate(Integer.parseInt(id), "Java Candidate4");
		store.save(candidate4);
		Assertions.assertEquals(store.findAllCandidates().size(), 3);
		Assertions.assertEquals(store.findAllCandidates().stream().filter(candidate -> candidate.getName().equals(candidate2.getName())).count(), 1);
		Assertions.assertEquals(store.findAllCandidates().stream().filter(candidate -> candidate.getName().equals(candidate3.getName())).count(), 1);
		Assertions.assertEquals(store.findAllCandidates().stream().filter(candidate -> candidate.getName().equals(candidate4.getName())).count(), 1);
		Assertions.assertEquals(store.findAllCandidates().stream().filter(candidate -> candidate.getName().equals(candidate1.getName())).count(), 0);
	}
	
	@Test
	public void whenFindAllPosts() {
		Store store = DbStore.instOf();
		Post post1 = new Post(0, "Java Job1");
		Post post2 = new Post(0, "Java Job2");
		Post post3 = new Post(0, "Java Job3");
		String id = store.save(post1);
		store.save(post2);
		store.save(post3);
		Post post4 = new Post(Integer.parseInt(id), "Java Job4");
		store.save(post4);
		Assertions.assertEquals(store.findAllPosts().size(), 3);
		Assertions.assertEquals(store.findAllPosts().stream().filter(post -> post.getName().equals(post2.getName())).count(), 1);
		Assertions.assertEquals(store.findAllPosts().stream().filter(post -> post.getName().equals(post3.getName())).count(), 1);
		Assertions.assertEquals(store.findAllPosts().stream().filter(post -> post.getName().equals(post4.getName())).count(), 1);
		Assertions.assertEquals(store.findAllPosts().stream().filter(post -> post.getName().equals(post1.getName())).count(), 0);
	}
}
