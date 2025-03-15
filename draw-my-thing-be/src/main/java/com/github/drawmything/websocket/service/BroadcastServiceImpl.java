package com.github.drawmything.websocket.service;

import static com.github.drawmything.websocket.model.StompPath.ROOM_START_PATH;
import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.room.api.RoomAssembler;
import com.github.drawmything.room.model.Room;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BroadcastServiceImpl implements BroadcastService {

  SimpMessagingTemplate stompBroadcast;

  @Override
  public void notifyRoomStarted(Room room) {
    stompBroadcast.convertAndSend(ROOM_START_PATH.getPath(room.getId()), RoomAssembler.toDto(room));
  }
}
