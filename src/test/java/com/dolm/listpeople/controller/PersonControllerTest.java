package com.dolm.listpeople.controller;

import com.dolm.listpeople.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @Test
  @DisplayName("Test getPersons - returns 200 status - if service returns people")
  void getPersons_Test() throws Exception {
    // given
    when(personService.findAll(anyString(), any(Pageable.class)))
      .thenReturn(new PageImpl<>(new ArrayList<>()));

    // when
    mockMvc.perform(MockMvcRequestBuilders.get("/person"))
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());

    // then
    verify(personService, times(1)).findAll(anyString(), any(Pageable.class));
  }
}
