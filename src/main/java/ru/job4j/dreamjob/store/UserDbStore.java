package ru.job4j.dreamjob.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
@Slf4j
@AllArgsConstructor
public class UserDbStore implements Store<User> {
	private final BasicDataSource pool;
	private final String findAll = "SELECT * FROM users";
	private final String findById = "SELECT * FROM users WHERE users.id = ?";
	private final String findByEmailAndPwd = "SELECT * FROM users WHERE users.email like ? and users.password like ?";
	private final String insert = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
	private final String update = "UPDATE users SET name = ?, email = ?, password = ? where id = ?";
	
	@Override
	public Collection<User> findAll() {
		List<User> users = new ArrayList<>();
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findAll)) {
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				while (rs.next()) {
					users.add(createUser(rs));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_ALL к базе данных: " + findAll);
			log.warn("Произошло исключение", e);
		}
		return users;
	}
	
	@Override
	public User findById(int id) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findById)) {
			st.setInt(1, id);
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				if (rs.next()) {
					return createUser(rs);
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_BY_ID к базе данных: " + findById);
			log.warn("Произошло исключение", e);
		}
		return null;
	}
	
	@Override
	public User add(User user) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(insert,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, user.getName());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPassword());
			st.execute();
			try (ResultSet it = st.getGeneratedKeys()) {
				if (it.next()) {
					user.setId(it.getInt(1));
					return user;
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе INSERT к базе данных: " + insert);
			log.warn("Произошло исключение", e);
		}
		return null;
	}
	
	@Override
	public User update(User user) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(update,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, user.getName());
			st.setString(2, user.getEmail());
			st.setObject(3, user.getPassword());
			st.setInt(4, user.getId());
			st.execute();
			try (ResultSet it = st.getGeneratedKeys()) {
				if (it.next()) {
					user.setId(it.getInt(1));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе UPDATE к базе данных: " + update);
			log.warn("Произошло исключение", e);
		}
		return user;
	}
	
	public Optional<User> registration(User user) {
		User add = add(user);
		return add == null ? Optional.empty() : Optional.of(add);
	}
	
	public Optional<User> findUserByEmailAndPwd(String email, String password) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findByEmailAndPwd)) {
			st.setString(1, email);
			st.setString(2, password);
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				if (rs.next()) {
					return Optional.of(createUser(rs));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_BY_ID к базе данных: " + findByEmailAndPwd);
			log.warn("Произошло исключение", e);
		}
		return Optional.empty();
	}
	
	private User createUser(ResultSet rs) throws SQLException {
		return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
	}
}
