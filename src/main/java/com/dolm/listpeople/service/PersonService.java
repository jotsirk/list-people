package com.dolm.listpeople.service;

import com.dolm.listpeople.model.Person;
import com.dolm.listpeople.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
public class PersonService {

  private final PersonRepository personRepository;

  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public void save(List<Person> persons) {
    if (CollectionUtils.isEmpty(persons)) {
      return;
    }

    personRepository.saveAll(persons);
  }

  public Page<Person> findAll(String name, Pageable pageable) {
    return personRepository.findByNameContainingIgnoreCase(name, pageable);
  }
}
