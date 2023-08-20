package com.dolm.listpeople.repository;

import com.dolm.listpeople.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PersonRepository extends JpaRepository<Person, Long> {

  Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
