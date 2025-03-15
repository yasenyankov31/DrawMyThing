package com.github.drawmything.websocket.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public enum StompPath {
  ROOM_START_PATH("/topic/room/start/%s");

  String path;

  public String getPath(Long roomId) {
    return String.format(path, String.valueOf(roomId));
  }
}
