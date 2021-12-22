package ru.job4j.dream.store;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.Prop;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DbStore implements Store {
	
	private static final String ERROR = "ошибка при выполнении запроса к базе данных";
	
	private final BasicDataSource pool = new BasicDataSource();
	
	private DbStore() {
		pool.setDriverClassName(Prop.getDataFromProperties("jdbc.driver"));
		pool.setUrl(Prop.getDataFromProperties("jdbc.url"));
		pool.setUsername(Prop.getDataFromProperties("jdbc.username"));
		pool.setPassword(Prop.getDataFromProperties("jdbc.password"));
		pool.setMinIdle(5);
		pool.setMaxIdle(10);
		pool.setMaxOpenPreparedStatements(100);
	}
	
	private static final class Lazy {
		private static final Store INST = new DbStore();
		
	}
	
	public static Store instOf() {
		return Lazy.INST;
	}
	
	public Collection<Post> findAllPosts() {
		List<Post> posts = new ArrayList<>();
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")) {
			try (ResultSet it = ps.executeQuery()) {
				while (it.next()) {
					posts.add(new Post(it.getInt("id"), it.getString("name")));
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return posts;
	}
	
	public Collection<Candidate> findAllCandidates() {
		List<Candidate> candidates = new ArrayList<>();
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates")) {
			try (ResultSet it = ps.executeQuery()) {
				while (it.next()) {
					candidates.add(new Candidate(it.getInt("id"), it.getString("name")));
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return candidates;
	}
	
	@Override
	public Collection<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM users")) {
			try (ResultSet it = ps.executeQuery()) {
				while (it.next()) {
					User user = new User();
					user.setId(it.getInt("id"));
					user.setName(it.getString("name"));
					user.setEmail(it.getString("email"));
					user.setPassword(it.getString("password"));
					users.add(user);
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return users;
	}
	
	public String save(Post post) {
		if (post.getId() == 0) {
			post = create(post);
		} else {
			update(post);
		}
		return String.valueOf(post.getId());
	}
	
	@Override
	public String save(User user) {
		user = create(user);
		return String.valueOf(user.getId());
	}
	
	public String save(Candidate candidate) {
		if (candidate.getId() == 0) {
			candidate = create(candidate);
		} else {
			update(candidate);
		}
		return String.valueOf(candidate.getId());
	}
	
	private void update(Candidate candidate) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("UPDATE candidates SET name = (?) where id = (?)")) {
			ps.setString(1, candidate.getName());
			ps.setInt(2, candidate.getId());
			ps.execute();
		} catch (Exception e) {
			log.error(ERROR, e);
		}
	}
	
	private Candidate create(Candidate candidate) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("INSERT INTO candidates(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, candidate.getName());
			ps.execute();
			try (ResultSet id = ps.getGeneratedKeys()) {
				if (id.next()) {
					candidate.setId(id.getInt(1));
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return candidate;
	}
	
	private User create(User user) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("INSERT INTO users (name, email, password) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.execute();
			try (ResultSet id = ps.getGeneratedKeys()) {
				if (id.next()) {
					user.setId(id.getInt(1));
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
			e.printStackTrace();
		}
		return user;
	}
	
	private Post create(Post post) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, post.getName());
			ps.execute();
			try (ResultSet id = ps.getGeneratedKeys()) {
				if (id.next()) {
					post.setId(id.getInt(1));
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return post;
	}
	
	private void update(Post post) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = (?) where id = (?)")) {
			ps.setString(1, post.getName());
			ps.setInt(2, post.getId());
			ps.execute();
		} catch (Exception e) {
			log.error(ERROR, e);
		}
	}
	
	public Post findByIdPost(Integer id) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
			ps.setInt(1, id);
			try (ResultSet it = ps.executeQuery()) {
				if (it.next()) {
					return new Post(it.getInt("id"), it.getString("name"));
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return null;
	}
	
	@Override
	public User findByEmail(String email) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
			ps.setString(1, email);
			try (ResultSet it = ps.executeQuery()) {
				if (it.next()) {
					User user = new User();
					user.setId(it.getInt("id"));
					user.setName(it.getString("name"));
					user.setEmail(it.getString("email"));
					user.setPassword(it.getString("password"));
					return user;
				}
			}
		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return null;
	}
	
	@Override
	public Candidate findByIdCandidate(Integer id) {
		Optional<Candidate> candidateOptional = findAllCandidates().stream().filter(candidate -> candidate.getId() == id).findAny();
		if (candidateOptional.isEmpty()) {
			return null;
		}
		return candidateOptional.get();
	}
	
	@Override
	public void deletePost(Integer id) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM post WHERE id = (?)")) {
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			log.error(ERROR, e);
		}
	}
	
	@Override
	public void deleteCandidate(Integer id) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM candidates WHERE id = (?)")) {
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			log.error(ERROR, e);
		}
	}
	
	@Override
	public void deleteUser(Integer id) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM users WHERE id = (?)")) {
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			log.error(ERROR, e);
		}
	}
}