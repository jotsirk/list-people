package com.dolm.listpeople.integration;

import com.dolm.listpeople.model.Person;
import com.dolm.listpeople.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GetPeopleIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PersonRepository personRepository;

  @Test
  public void testGetPersons() throws Exception {
    Person person = new Person();
    person.setName("John");
    personRepository.save(person);

    mockMvc.perform(get("/person?name=John"))
      .andExpect(status().isOk());
  }
}

