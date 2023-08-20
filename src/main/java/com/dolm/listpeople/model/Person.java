package com.dolm.listpeople.model;

import com.dolm.listpeople.model.dto.PersonDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  public Person(String name, String photoUrl) {
    this.name = name;
    this.photoUrl = photoUrl;
  }

  public PersonDTO toDto() {
    return new PersonDTO(name, photoUrl);
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String photoUrl;
}
