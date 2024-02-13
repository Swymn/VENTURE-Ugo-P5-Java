package fr.swynn.core;

import fr.swynn.model.Person;
import fr.swynn.service.PersonAlreadyExist;
import fr.swynn.service.UnknownPerson;

import java.util.List;

public interface PersonService {

    List<String> getCommunityEmail(String city);

    List<Person> getPersonByAddress(String address);

    List<Person> getPersonByFirstAndLastName(String firstName, String lastName);

    Person deletePerson(Person persona) throws UnknownPerson;

    Person updatePerson(Person persona) throws UnknownPerson;

    Person createPerson(Person persona) throws PersonAlreadyExist;

}