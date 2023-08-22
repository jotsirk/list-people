package com.dolm.listpeople.repository;

import com.dolm.listpeople.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PersonRepositoryTest {

  @Autowired
  private PersonRepository personRepository;

  @AfterEach
  void after() {
    personRepository.deleteAll();
  }

  @Test
  @DisplayName("saveAll - saves list of people - if list is not empty")
  void saveAllTest() {
    // given
    List<Person> people = createPeopleList();

    // when
    personRepository.saveAll(people);

    // then
    List<Person> savedPeople = personRepository.findAll();
    assertEquals(people.size(), savedPeople.size());
  }

  @Test
  @DisplayName("findByNameContainingIgnoreCase - returns list of - ")
  void findByNameContainingIgnoreCaseTest() {
    // given
    personRepository.saveAll(createPeopleList());
    String filterNameValue = "jo";

    // when
    Page<Person> filteredPeople = personRepository.findByNameContainingIgnoreCase(filterNameValue, Pageable.ofSize(5));

    // then
    assertEquals(4, filteredPeople.getTotalElements());
  }

  private List<Person> createPeopleList() {
    return List.of(
      new Person("Joseph Sea", "photo.jpg"),
      new Person("Joe Sea", "photo.jpg"),
      new Person("Sea Joe", "photo.jpg"),
      new Person("John Sea", "photo.jpg"),
      new Person("Frank Ocean", "photo.jpg"),
      new Person("Billy Shoot", "photo.jpg")
    );
  }

}
