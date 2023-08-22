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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonService personService;

  @Test
  @DisplayName("findAll - returns page of 1 element - delegates to repository")
  public void findAllTest() {
    // given
    String filterNameValue = "Jo";
    Pageable pageable = PageRequest.of(0, 5);
    Page<Person> expectedPage = new PageImpl<>(List.of(new Person("John", "john.jpg")));

    when(personRepository.findByNameContainingIgnoreCase(filterNameValue, pageable)).thenReturn(expectedPage);

    // when
    Page<Person> resultPage = personService.findAll(filterNameValue, pageable);

    // then
    assertEquals(expectedPage, resultPage);
  }

  @Test
  @DisplayName("save")
  void saveTest() {
    // given

    // when

    // then
  }
}
