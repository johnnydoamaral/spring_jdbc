package com.johnny.springjdbc.test;

import static org.junit.Assert.*;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.johnny.springjdbc.dao.PersonDAO;
import com.johnny.springjdbc.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDAOTests {

	@Autowired
	PersonDAO dao;

	@Test
	public void testFindAll() {
		assertThat(dao.findAllPersons(), is(not(empty())));
	}

	@Test
	public void testFindById() {;
		int existentID = 1001;
		int nonExistentID = 9999;
		assertThat(dao.findPersonById(existentID), is(notNullValue()));
		assertThat(dao.findPersonById(nonExistentID), is(nullValue()));

	}

	@Test
	public void testFindByName() {
		assertThat(dao.findPersonByName("johnny"), is(not(empty())));
		assertThat(dao.findPersonByName("Johnny"), is(not(empty())));
		assertThat(dao.findPersonByName("JOHNNY"), is(not(empty())));
	}

	@Test
	public void testFindByLocation() {
		String inexistentLocation = "SomeInexistentLocation";
		assertThat(dao.findPersonByLocation("brazil"), is(not(empty())));
		assertThat(dao.findPersonByLocation("Brazil"), is(not(empty())));
		assertThat(dao.findPersonByLocation("BRAZIL"), is(not(empty())));
		assertThat(dao.findPersonByLocation(inexistentLocation), is(empty()));
	}
	
	@Test
	public void testCreatePerson() {
		Person newPerson = new Person(1004, "Juan", "Spain", new Date());
		assertThat(dao.createPerson(newPerson), is(not(0)));
		assertThat(dao.findPersonById(newPerson.getId()), is(notNullValue()));
	}
	
	@Test
	public void testUpdatePerson() {
		Person existingPerson = dao.findPersonById(1002);
		String newName = existingPerson.getName() + " Smith";
		existingPerson.setName(newName);
		assertThat(dao.updatePerson(existingPerson), is(not(0)));
		existingPerson = dao.findPersonById(1002);
		assertThat(existingPerson.getName(), is(newName));
	}
	
	@Test
	public void testDeleteById() {
		Person newPerson = new Person(1005, "Marco", "Croatia", new Date());
		assertThat(dao.createPerson(newPerson), is(not(0)));
		assertThat(dao.findPersonById(newPerson.getId()), is(notNullValue()));
		assertThat(dao.deletePersonById(newPerson.getId()), is(not(0)));
		assertThat(dao.findPersonById(newPerson.getId()), is(nullValue()));
		Person newPersonNotPresentInDB = new Person(1006, "Ryan", "United States", new Date());
		assertThat(dao.deletePersonById(newPersonNotPresentInDB.getId()), is(0));
	}
}
