package com.dolm.listpeople.service;

import com.dolm.listpeople.model.Person;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CsvImportService {

  private final ApplicationContext appContext;

  private final PersonService personService;

  // TODO add logger

  public CsvImportService(PersonService personService, ApplicationContext appContext) {
    this.personService = personService;
    this.appContext = appContext;
  }

  @PostConstruct
  void ImportInitialData() {
    // TODO maybe even just use the liquibase csv importer straight into DB
    List<Person> personsImportList = new ArrayList<>(Collections.emptyList());

    try {
      Resource[] resources = appContext.getResources("classpath:import_data/*.*");
      for (Resource resource : resources) {
        this.loadData(resource).forEach(record -> {
          // TODO skip the first line
          personsImportList.add(new Person(record.get(0), record.get(1)));
        });
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    personService.save(personsImportList);
  }

  private List<CSVRecord> loadData(Resource resource) {
    List<CSVRecord> csvData;
    try (InputStream inputStream = resource.getInputStream()) {
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      CSVParser csvParser = new CSVParser(inputStreamReader, CSVFormat.DEFAULT);
      csvData = csvParser.getRecords();
    } catch (IOException e) {
      // TODO implement proper exception
      throw new RuntimeException(e);
    }

    return csvData;
  }
}
