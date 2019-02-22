package com.johnny.springjdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.johnny.springjdbc.model.Person;

@Repository
public class JdbcPersonDAO implements PersonDAO {

	@Autowired
	JdbcTemplate springJdbc;

	@Override
	public List<Person> findAllPersons() {
		// Using BeanPropertyRowMapper instead of a custom RowMapper
		return springJdbc.query("select * from person", new BeanPropertyRowMapper<Person>(Person.class));
	}

	@Override
	public Person findPersonById(int id) {
		// Using a custom RowMapper defined below as an inner class
		Person person;
		try {
			person = springJdbc.queryForObject("select * from person where id=?", new PersonRowMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			person = null;
		}
		return person;
	}

	@Override
	public List<Person> findPersonByName(String name) {
		// Using a lambda expression instead of a RowMapper
		return springJdbc.query("select * from person where upper(name) like upper(?)", (rs, rowNum) -> {
			Person p = new Person();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setLocation(rs.getString("location"));
			p.setBirthDate(rs.getDate("birth_date"));
			return p;
		}, name);
	}

	@Override
	public List<Person> findPersonByLocation(String location) {
		return springJdbc.query("select * from person where upper(location) like upper(?)", (rs, rowNum) -> {
			Person p = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("location"),
					rs.getDate("birth_date"));
			return p;
		}, location);
	}

	class PersonRowMapper implements RowMapper<Person> {

		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			// You map the object's fields according to the columns
			// returned in the ResultSet
			Person person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("location"),
					rs.getDate("birth_date"));
			return person;
		}

	}

	@Override
	public int deletePersonById(int id) {
		return springJdbc.update("delete from person where id=?", id);
	}

	@Override
	public int createPerson(Person person) {
		return springJdbc.update("insert into person (id, name, location, birth_date) values (?, ?, ?, ?)",
				person.getId(), person.getName(), person.getLocation(), new Timestamp(person.getBirthDate().getTime()));
	}

	@Override
	public int updatePerson(Person person) {
		return springJdbc.update("update person set name = ?, location = ?, birth_date = ? where id = ?",
				person.getName(), person.getLocation(), new Timestamp(person.getBirthDate().getTime()), person.getId());
	}

}
