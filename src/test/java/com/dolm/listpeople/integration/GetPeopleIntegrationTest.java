package com.dolm.listpeople.integration;

import com.dolm.listpeople.model.Person;
import com.dolm.listpeople.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class GetPeopleIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PersonRepository personRepository;

  @BeforeEach
  void setUp() {
    personRepository.saveAll(createPeopleList());
  }

  @AfterEach
  void tearDown() {
    personRepository.deleteAll();
  }

  @Test
  @DisplayName("getPeople - returns 200 status and expected people - if no errors")
  void getPeople_validFilter_Test() throws Exception {
    // when & then
    mockMvc.perform(get("/person?name=Joseph"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.content[0].name").value("Joseph Sea"))
      .andExpect(jsonPath("$.content[0].imageUrl").value("photo.jpg"));
  }

  @Test
  @DisplayName("getPeople - returns 200 status and all people - if no errors and no filter")
  void getPeople_missingFilter_Test() throws Exception {
    // when & then
    mockMvc.perform(get("/person"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.content.length()").value(6));
  }

  @Test
  @DisplayName("getPeople - returns 200 status and no people - if no errors and filter that does not exist in db")
  void getPeople_missingFilterReturn_Test() throws Exception {
    // when & then
    mockMvc.perform(get("/person?name=joosep"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.content.length()").value(0));
  }

  @Test
  @DisplayName("getPeople - returns 404 status - if querying missing resource")
  void getPeople_ErrorTest() throws Exception {
    // when & then
    mockMvc.perform(get("/perso?"))
      .andExpect(status().is4xxClientError());
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

