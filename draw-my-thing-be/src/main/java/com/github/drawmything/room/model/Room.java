package com.github.drawmything.room.model;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class Room {

  @Id @GeneratedValue Long id;

  @Column(nullable = false, unique = true)
  UUID customUuid;

  @Column(nullable = false)
  Short drawtime;

  @Column(nullable = false)
  Byte rounds;

  @Column(nullable = false)
  Byte wordsCount;
}
