package com.johnny.springjdbc.dao;

import java.util.List;

import com.johnny.springjdbc.model.Person;

public interface PersonDAO {

	List<Person> findAllPersons();

	Person findPersonById(int id);

	List<Person> findPersonByName(String name);

	List<Person> findPersonByLocation(String location);
	
	int deletePersonById(int id);
	
	int createPerson(Person person);
	
	int updatePerson(Person person);

}
