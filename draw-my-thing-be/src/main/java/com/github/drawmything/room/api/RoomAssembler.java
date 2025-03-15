package com.github.drawmything.room.api;

import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.room.model.Room;
import com.github.drawmything.room.model.dto.RoomModel;
import com.github.drawmything.room.model.dto.RoomParticipationModel;
import com.github.drawmything.room.model.participation.RoomParticipation;
import com.github.drawmything.user.api.UserAssembler;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class RoomAssembler {

  public static final RoomModel toDto(Room room) {
    return RoomModel.builder()
        .id(room.getId())
        .drawtime(room.getDrawtime())
        .rounds(room.getRounds())
        .wordsCount(room.getWordsCount())
        .status(room.getStatus())
        .participations(
            room.getParticipations().stream().map(RoomAssembler::toRoomParticipationModel).toList())
        .build();
  }

  private static RoomParticipationModel toRoomParticipationModel(RoomParticipation participation) {
    return RoomParticipationModel.builder()
        .user(UserAssembler.toDto(participation.getPercipient()))
        .score(participation.getScore())
        .build();
  }
}
