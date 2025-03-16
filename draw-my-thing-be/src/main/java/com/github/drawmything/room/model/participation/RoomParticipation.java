package com.github.drawmything.room.model.participation;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.room.model.Room;
import com.github.drawmything.user.model.User;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class RoomParticipation {

  @Id @GeneratedValue Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  Room room;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "percipient_id", nullable = false)
  User percipient;

  @Column(nullable = false)
  Integer score;

  @ElementCollection
  @CollectionTable(
      name = "participation_guess",
      joinColumns = @JoinColumn(name = "room_participation_id"))
  @Column(name = "guess")
  @Builder.Default
  List<String> guesses = new LinkedList<>();
}
