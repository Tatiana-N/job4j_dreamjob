package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.Prop;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DbStore implements Store {
	
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return candidates;
	}
	
	public String save(Post post) {
		if (post.getId() == 0) {
			post = create(post);
		} else {
			update(post);
		}
		return String.valueOf(post.getId());
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return candidate;
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
			e.printStackTrace();
		}
		return post;
	}
	
	private void update(Post post) {
		try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = (?) where id = (?)")) {
			ps.setString(1, post.getName());
			ps.setInt(2, post.getId());
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return null;
	}
}