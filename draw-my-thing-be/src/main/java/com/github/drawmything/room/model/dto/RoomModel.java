package com.github.drawmything.room.model.dto;

import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.room.model.RoomStatus;
import java.util.LinkedList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class RoomModel {

  Long id;
  Short drawtime;
  Byte rounds;
  Byte wordsCount;
  RoomStatus status;

  @Builder.Default List<RoomParticipationModel> participations = new LinkedList<>();
}
