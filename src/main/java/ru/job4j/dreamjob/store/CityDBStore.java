package ru.job4j.dreamjob.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import net.jcip.annotations.ThreadSafe;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ThreadSafe
@Repository
@Slf4j
@AllArgsConstructor
public class CityDBStore implements Store<City> {
	private final BasicDataSource pool;
	private final String findById = "SELECT * FROM cities WHERE id = ?";
	private final String findAll = "SELECT * FROM cities";
	private final String insert = "INSERT INTO cities (name) VALUES (?)";
	
	public Collection<City> findAll() {
		List<City> posts = new ArrayList<>();
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findAll)) {
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				while (rs.next()) {
					posts.add(createCity(rs));
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_ALL к базе данных: ", e);
		}
		return posts;
	}
	
	@Override
	public City add(City city) {
			try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(insert,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				st.setString(1, city.getName());
				st.execute();
				try (ResultSet it = st.getGeneratedKeys()) {
					if (it.next()) {
						city.setId(it.getInt(1));
					}
				}
			} catch (SQLException e) {
				log.warn("Произошло исключение при запросе INSERT к базе данных: " + insert);
				log.warn("Произошло исключение", e);
			}
			return city;
		}
	
	@Override
	public City update(City city) {
		return null;
	}
	
	public City findById(int id) {
		try (Connection cn = pool.getConnection(); PreparedStatement st = cn.prepareStatement(findById)) {
			st.setInt(1, id);
			st.executeQuery();
			try (ResultSet rs = st.getResultSet()) {
				if (rs.next()) {
					return createCity(rs);
				}
			}
		} catch (SQLException e) {
			log.warn("Произошло исключение при запросе FIND_BY_ID к базе данных: ", e);
		}
		return null;
	}
	
	private City createCity(ResultSet rs) throws SQLException {
		return new City(rs.getInt("id"), rs.getString("name"));
	}
}