package com.dolm.listpeople.controller;

import com.dolm.listpeople.model.Person;
import com.dolm.listpeople.model.dto.PersonDTO;
import com.dolm.listpeople.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @CrossOrigin(origins = "${allowed.origins}")
  @GetMapping("")
  public ResponseEntity<Page<PersonDTO>> getPeople(
    @RequestParam(required = false, defaultValue = "") String name,
    Pageable pageable
  ) {
    Page<PersonDTO> persons = personService.findAll(name, pageable)
      .map(Person::toDto);

    return ResponseEntity.ok(persons);
  }
}
