package com.github.drawmything.room.service;

import com.github.drawmything.room.model.Room;
import com.github.drawmything.room.model.request.RoomCreateRequest;

public interface RoomService {

  Room create(RoomCreateRequest request);

  Room addCurrentUser(Long roomId);

  Room start(Long roomId);

  Room setActiveWord(Long id, String word);
}
