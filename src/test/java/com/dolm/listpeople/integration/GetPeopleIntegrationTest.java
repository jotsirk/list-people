package com.dolm.listpeople.integration;

import org.junit.jupiter.api.DisplayName;
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

  @Test
  @DisplayName("getPeople - returns 200 status - if no errors")
  void getPeople_Test() throws Exception {
    // then
    mockMvc.perform(get("/person?name=o"))
      .andExpect(status().isOk());
  }
}

