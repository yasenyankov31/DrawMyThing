package com.github.drawmything.user.model;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Table(name = "`users`")
@Entity
@Data
@Builder
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class User {

  @Id @GeneratedValue Long id;

  @Column(length = 100, unique = true)
  String username;

  @Column(length = 100)
  String password;
}
