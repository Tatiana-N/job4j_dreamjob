package ru.job4j.dreamjob.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import net.jcip.annotations.ThreadSafe;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ThreadSafe
@Repository
@Slf4j
@AllArgsConstructor
public class PostDbStore implements Store<Post> {
	private final BasicDataSource pool;
	private final String findAll = "SELECT * FROM posts as p join cities c on c.id = p.city_id";
	private final String findById = "SELECT * FROM posts as  p join cities c on c.id = p.city_id WHERE p.id = ?";
	private final String insert = "INSERT INTO posts (name, description, created, city_id) VALUES (?, ?, ?, ?)";
	private final String update = "UPDATE posts SET name = ?, description = ?, created = ?, city_id = ? where id = ?";
	
	
	public Collection<Post> findAll() {
		List<Post> posts = new ArrayList<>();
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findAll)) {
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				while (rs.next()) {
					posts.add(createPost(rs));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_ALL к базе данных: ", e);
		}
		return posts;
	}
	
	public Post findById(int id) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findById)) {
			st.setInt(1, id);
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				if (rs.next()) {
					return createPost(rs);
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_BY_ID к базе данных: ", e);
		}
		return null;
	}
	
	public Post add(Post post) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(insert,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, post.getName());
			st.setString(2, post.getDescription());
			st.setObject(3, Timestamp.valueOf(LocalDateTime.now()));
			st.setInt(4, post.getCity().getId());
			st.execute();
			try (ResultSet it = st.getGeneratedKeys()) {
				if (it.next()) {
					post.setId(it.getInt(1));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе INSERT к базе данных: ", e);
		}
		return post;
	}
	
	public Post update(Post post) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(update,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, post.getName());
			st.setString(2, post.getDescription());
			st.setObject(3, Timestamp.valueOf(LocalDateTime.now()));
			st.setInt(4, post.getCity().getId());
			st.setInt(5, post.getId());
			st.executeQuery();
			try (ResultSet it = st.getGeneratedKeys()) {
				if (it.next()) {
					post.setId(it.getInt(1));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе UPDATE к базе данных: ", e);
		}
		return post;
	}
	
	private Post createPost(ResultSet rs) throws SQLException {
		Post post = new Post(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getObject("created",
				Timestamp.class).toLocalDateTime());
		post.setCity(new City(rs.getInt("city_id"), rs.getString(7)));
		return post;
	}
}
