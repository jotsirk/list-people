package com.dolm.listpeople.service;

import com.dolm.listpeople.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
  @DisplayName("importInitialData - successfully imports data - if resources exist and valid")
  void importInitialData_successfulImport_Test() throws IOException {
    // given
    String[] profiles = new String[]{"dev"};
    when(env.getActiveProfiles()).thenReturn(profiles);

    Resource mockResource1 = mock(Resource.class);
    Resource mockResource2 = mock(Resource.class);
    when(appContext.getResources("classpath:import_data/*.*"))
      .thenReturn(new Resource[]{mockResource1, mockResource2});

    InputStream mockInputStream1 = new ByteArrayInputStream("John smith1,http://image.com\nJohn smith2,http://image.com".getBytes());
    InputStream mockInputStream2 = new ByteArrayInputStream("John smith3,http://image.com\nJohn smith4,http://image.com".getBytes());

    when(mockResource1.getInputStream()).thenReturn(mockInputStream1);
    when(mockResource2.getInputStream()).thenReturn(mockInputStream2);

    // when
    csvImportService.importInitialData();

    // then
    ArgumentCaptor<List<Person>> captor = ArgumentCaptor.forClass(List.class);
    verify(personService).save(captor.capture());
    List<Person> savedPersons = captor.getValue();

    assertThat(savedPersons).hasSize(2);
    assertThat(savedPersons)
      .usingElementComparator(comparing(Person::getName).thenComparing(Person::getPhotoUrl))
      .containsExactly(
        new Person("John smith2", "http://image.com"),
        new Person("John smith4", "http://image.com")
      );
  }

  @Test
  @DisplayName("importInitialData - tries to save empty list - if valid profile, files exist but no data")
  void importInitialData_noCSVFiles_Test() throws IOException {
    // given
    String[] profiles = new String[]{"dev"};
    when(env.getActiveProfiles()).thenReturn(profiles);

    when(appContext.getResources("classpath:import_data/*.*"))
      .thenReturn(new Resource[]{}); // No resources found

    // when
    csvImportService.importInitialData();

    // then
    verify(personService).save(Collections.emptyList());
  }

  @Test
  @DisplayName("importInitialData - throws Exception - if db action throws some exception")
  void importInitialData_DBException_Test() throws IOException {
    // given
    String[] profiles = new String[]{"dev"};
    when(env.getActiveProfiles()).thenReturn(profiles);

    Resource mockResource1 = mock(Resource.class);
    when(appContext.getResources("classpath:import_data/*.*"))
      .thenReturn(new Resource[]{mockResource1});

    // when
    assertThrows(RuntimeException.class, () -> csvImportService.importInitialData());

    // then
    verify(personService, never()).save(any());
  }
}
