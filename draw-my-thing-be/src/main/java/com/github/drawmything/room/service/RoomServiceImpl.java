package com.github.drawmything.room.service;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.room.model.Room;
import com.github.drawmything.room.model.RoomRepository;
import com.github.drawmything.room.model.request.RoomCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoomServiceImpl implements RoomService {

  RoomRepository roomRepository;

  @Override
  public Room create(RoomCreateRequest request) {
    var room =
        Room.builder()
            .customUuid(randomUUID())
            .drawtime(request.drawtime().shortValue())
            .rounds(request.rounds().byteValue())
            .wordsCount(request.wordsCount().byteValue())
            .build();
    return roomRepository.save(room);
  }
}
