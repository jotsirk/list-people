package com.dolm.listpeople.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvImportServiceTest {

  @Mock
  private ApplicationContext appContext;

  @Mock
  private PersonService personService;

  @Mock
  private Environment env;

  @InjectMocks
  private CsvImportService csvImportService;

  @Test
  @DisplayName("importInitialData - does nothing - if active profile contains prod")
  void importInitialData_prodEnv_Test() throws IOException {
    // given
    String[] profiles = new String[]{"prod"};
    when(env.getActiveProfiles()).thenReturn(profiles);

    // when
    csvImportService.importInitialData();

    // then
    verify(appContext, never()).getResources(any());
    verify(personService, never()).save(any());
  }

  @Test
  @DisplayName("importInitialData - imports initial data - if active profile does not contain prod")
  void importInitialData_devEnv_Test() throws IOException {
    // given
    String[] profiles = new String[]{"dev"};
    when(env.getActiveProfiles()).thenReturn(profiles);
//    when(appContext.getResources("location")).thenReturn()

    // when
    csvImportService.importInitialData();

    // then
    verify(appContext, never()).getResources(any());
    verify(personService, never()).save(any());
  }
}
