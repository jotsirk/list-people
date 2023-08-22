package com.dolm.listpeople.service;

import com.dolm.listpeople.model.Person;
import com.dolm.listpeople.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonService personService;

  @Test
  @DisplayName("findAll - delegates to repository and return one page element - if no exceptions")
  void findAllTest() {
    // given
    String filterNameValue = "Jo";
    Pageable pageable = PageRequest.of(0, 5);
    Page<Person> expectedPage = new PageImpl<>(List.of(new Person("John Name", "john.jpg")));

    when(personRepository.findByNameContainingIgnoreCase(filterNameValue, pageable)).thenReturn(expectedPage);

    // when
    Page<Person> resultPage = personService.findAll(filterNameValue, pageable);

    // then
    assertEquals(expectedPage, resultPage);
  }

  @Test
  @DisplayName("save - delegates to repository and saves list of people - if no exceptions")
  void saveTest() {
    // given
    Person saveablePerson = new Person("John Name", "photo.jpg");

    // when
    personService.save(Collections.singletonList(saveablePerson));

    // then
    verify(personRepository).saveAll(Collections.singletonList(saveablePerson));
  }

  @Test
  @DisplayName("save - returns - if empty list")
  void saveEmptyListTest() {
    // when
    personService.save(Collections.emptyList());

    // then
    verify(personRepository, never()).saveAll(any());
  }

  @Test
  @DisplayName("save - returns - if trying to save null")
  void saveNullTest() {
    // when
    personService.save(null);

    // then
    verify(personRepository, never()).saveAll(any());
  }
}
