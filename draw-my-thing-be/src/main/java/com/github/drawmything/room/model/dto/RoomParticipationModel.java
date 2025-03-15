package com.github.drawmything.room.model.dto;

import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.user.model.dto.UserModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class RoomParticipationModel {

  UserModel user;
  Integer score;
}
