package com.dolm.listpeople.service;

import com.dolm.listpeople.model.Person;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ch.qos.logback.core.CoreConstants.DOUBLE_QUOTE_CHAR;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class CsvImportService {

  private static final Character COMMA_DELIMITER = ',';
  private static final String IMPORT_ERROR_MSG = "Error loading CSV data";
  private static final String IMPORT_SUCCESS_MSG = "Successfully imported %s lines";

  private static final CSVFormat CSV_FORMAT = CSVFormat.Builder.create()
    .setDelimiter(COMMA_DELIMITER)
    .setQuote(DOUBLE_QUOTE_CHAR)
    .setIgnoreEmptyLines(true)
    .setAllowDuplicateHeaderNames(true)
    .setSkipHeaderRecord(true)
    .build();
  private static final Logger log = LoggerFactory.getLogger(CsvImportService.class);

  private final ApplicationContext appContext;
  private final PersonService personService;
  private final Environment env;

  public CsvImportService(PersonService personService, ApplicationContext appContext, Environment env) {
    this.personService = personService;
    this.appContext = appContext;
    this.env = env;
  }

  @PostConstruct
  public void importInitialData() {
    // TODO maybe even just use the liquibase csv importer straight into DB
    String[] activeProfiles = env.getActiveProfiles();
    if (!Arrays.asList(activeProfiles).contains("prod")) {
      List<Person> personsImportList = new ArrayList<>(Collections.emptyList());

      try {
        Resource[] resources = appContext.getResources("classpath:import_data/*.*");
        for (Resource resource : resources) {
          List<Person> persons = loadData(resource).stream()
            // First is header and the format for some reason fails to skip this
            .skip(1)
            .map(csvRecord -> new Person(csvRecord.get(0), csvRecord.get(1)))
            .toList();
          personsImportList.addAll(persons);
        }
      } catch (IOException e) {
        log.error(IMPORT_ERROR_MSG, e);
      }

      personService.save(personsImportList);
      log.info(IMPORT_SUCCESS_MSG);
    }
  }

  private List<CSVRecord> loadData(Resource resource) throws IOException {
    try (
      InputStream inputStream = resource.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
      CSVParser csvParser = new CSVParser(inputStreamReader, CSV_FORMAT)
    ) {
      return csvParser.getRecords();
    }
  }
}
