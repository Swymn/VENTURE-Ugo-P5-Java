package fr.swynn.core;

import fr.swynn.model.Person;

import java.util.List;

public interface PersonService {

    List<String> getCommunityEmail(String city);

    List<Person> getPersonByAddress(String address);

    Person deletePerson(Person persona) throws UnknownPerson;

    Person updatePerson(Person persona) throws UnknownPerson;

    Person createPerson(Person persona) throws PersonAlreadyExist;

}