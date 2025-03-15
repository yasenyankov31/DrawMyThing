package com.github.drawmything.room.model;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.room.model.participation.RoomParticipation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;
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

  @Builder.Default
  @OneToMany(fetch = LAZY, mappedBy = "room")
  List<RoomParticipation> participations = new LinkedList<>();
}
