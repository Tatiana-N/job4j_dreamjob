package ru.job4j.dreamjob.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Candidate;

import javax.annotation.concurrent.ThreadSafe;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ThreadSafe
@Repository
@Slf4j
@AllArgsConstructor
public class CandidateDbStore implements Store<Candidate> {
	private final BasicDataSource pool;
	private final String findAll = "SELECT * FROM candidates";
	private final String findById = "SELECT * FROM candidates WHERE candidates.id = ?";
	private final String insert = "INSERT INTO candidates (name, description, created) VALUES (?, ?, ?)";
	private final String update = "UPDATE candidates SET name = ?, description = ?, created = ? where id = ?";
	
	
	public Collection<Candidate> findAll() {
		List<Candidate> candidates = new ArrayList<>();
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findAll)) {
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				while (rs.next()) {
					candidates.add(createPost(rs));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_ALL к базе данных: " + findById);
			log.warn("Произошло исключение", e);
		}
		return candidates;
	}
	
	public Candidate findById(int id) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findById)) {
			st.setInt(1, id);
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				if (rs.next()) {
					return createPost(rs);
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_BY_ID к базе данных: " + findById);
			log.warn("Произошло исключение", e);
		}
		return null;
	}
	
	public Candidate add(Candidate candidate) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(insert,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, candidate.getName());
			st.setString(2, candidate.getDescription());
			st.setObject(3, Timestamp.valueOf(LocalDateTime.now()));
			st.execute();
			try (ResultSet it = st.getGeneratedKeys()) {
				if (it.next()) {
					candidate.setId(it.getInt(1));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе INSERT к базе данных: " + insert);
			log.warn("Произошло исключение", e);
		}
		return candidate;
	}
	
	public Candidate update(Candidate candidate) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(update,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, candidate.getName());
			st.setString(2, candidate.getDescription());
			st.setObject(3, Timestamp.valueOf(LocalDateTime.now()));
			st.setInt(4, candidate.getId());
			st.executeQuery();
			try (ResultSet it = st.getGeneratedKeys()) {
				if (it.next()) {
					candidate.setId(it.getInt(1));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе UPDATE к базе данных: " + update);
			log.warn("Произошло исключение", e);
		}
		return candidate;
	}
	
	private Candidate createPost(ResultSet rs) throws SQLException {
		return new Candidate(rs.getInt("id"),
				rs.getString("name"),
				rs.getString("description"),
				rs.getObject("created",
				Timestamp.class).toLocalDateTime());
	}
}
