import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.servlet.CandidateServlet;
import ru.job4j.dream.servlet.PostServlet;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServletTest {
	@BeforeEach
	public void before() {
		Store store = DbStore.instOf();
		Collection<Candidate> allCandidates = store.findAllCandidates();
		Collection<Post> allPosts = store.findAllPosts();
		Collection<User> allUsers = store.findAllUsers();
		allCandidates.forEach(candidate -> store.deleteCandidate(candidate.getId()));
		allPosts.forEach(post -> store.deletePost(post.getId()));
		allUsers.forEach(user -> store.deleteUser(user.getId()));
	}
	
	@Test
	public void whenCreatePost() throws IOException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		when(req.getParameter("id")).thenReturn("0");
		when(req.getParameter("name")).thenReturn("name of new post");
		new PostServlet().doPost(req, resp);
		Assertions.assertEquals(DbStore.instOf()
				.findAllPosts()
				.stream()
				.filter(p -> p.getName().equals("name of new post"))
				.count(), 1);
	}
	
	@Test
	public void whenCreateCandidate() throws IOException, ServletException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		when(req.getParameter("id")).thenReturn("0");
		when(req.getParameter("name")).thenReturn("name of new candidate");
		new CandidateServlet().doPost(req, resp);
		Assertions.assertEquals(DbStore.instOf()
				.findAllCandidates()
				.stream()
				.filter(p -> p.getName().equals("name of new candidate"))
				.count(), 1);
	}
	
	@Test
	public void whenDeleteCandidate() throws IOException, ServletException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		Store store = DbStore.instOf();
		Candidate candidate = new Candidate(0, "newCandidate");
		String id = store.save(candidate);
		Assertions.assertNotNull(DbStore.instOf()
				.findByIdCandidate(Integer.parseInt(id)));
		when(req.getParameter("id")).thenReturn(id);
		new CandidateServlet().doDelete(req, resp);
		Assertions.assertNull(DbStore.instOf()
				.findByIdCandidate(Integer.parseInt(id)));
	}
	
	@Test
	public void whenDeletePost() throws IOException, ServletException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		Store store = DbStore.instOf();
		Post post = new Post(0, "1");
		String id = store.save(post);
		Assertions.assertNotNull(DbStore.instOf()
				.findByIdPost(Integer.parseInt(id)));
		when(req.getParameter("id")).thenReturn(id);
		new PostServlet().doDelete(req, resp);
		Assertions.assertNull(DbStore.instOf()
				.findByIdPost(Integer.parseInt(id)));
	}
}
