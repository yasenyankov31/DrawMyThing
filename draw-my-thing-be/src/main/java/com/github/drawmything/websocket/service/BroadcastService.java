package com.github.drawmything.websocket.service;

import com.github.drawmything.room.model.Room;

public interface BroadcastService {

  void notifyRoomStarted(Room room);
}
