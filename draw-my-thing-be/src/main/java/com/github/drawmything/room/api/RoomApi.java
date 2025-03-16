package com.github.drawmything.room.api;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.github.drawmything.room.model.request.RoomCreateRequest;
import com.github.drawmything.room.service.RoomService;
import com.github.drawmything.websocket.service.BroadcastService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoomApi {

  RoomService roomService;
  BroadcastService broadcastService;

  @ResponseStatus(CREATED)
  @PostMapping
  public String create(@Valid @RequestBody RoomCreateRequest request) {
    var room = roomService.create(request);
    return room.getCustomUuid().toString();
  }

  @ResponseStatus(OK)
  @PatchMapping("{id}/join")
  public ResponseEntity<Void> join(@PathVariable Long id) {
    roomService.addCurrentUser(id);
    return ResponseEntity.ok().build();
  }

  @ResponseStatus(OK)
  @PatchMapping("{id}/start")
  public ResponseEntity<Void> start(@PathVariable Long id) {
    var room = roomService.start(id);
    broadcastService.notifyRoomStarted(room);
    return ResponseEntity.ok().build();
  }

  @ResponseStatus(OK)
  @PatchMapping("{id}/word")
  public ResponseEntity<Void> setActiveWord(@PathVariable Long id, @RequestBody String word) {
    roomService.setActiveWord(id, word);
    return ResponseEntity.ok().build();
  }
}
