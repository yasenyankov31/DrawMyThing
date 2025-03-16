package com.github.drawmything.room.service;

import static com.github.drawmything.room.model.RoomStatus.*;
import static com.github.drawmything.util.SecurityUtils.getCurrentUsername;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;

import com.github.drawmything.room.model.Room;
import com.github.drawmything.room.model.RoomRepository;
import com.github.drawmything.room.model.participation.RoomParticipation;
import com.github.drawmything.room.model.participation.RoomParticipationRepository;
import com.github.drawmything.room.model.request.RoomCreateRequest;
import com.github.drawmything.user.service.UserService;
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
  RoomParticipationRepository participationRepository;

  UserService userService;

  @Override
  public Room create(RoomCreateRequest request) {
    var room =
        Room.builder()
            .customUuid(randomUUID())
            .drawtime(request.drawtime().shortValue())
            .rounds(request.rounds().byteValue())
            .wordsCount(request.wordsCount().byteValue())
            .status(CREATED)
            .build();
    room = roomRepository.save(room);
    createParticipationWithCurrentUser(room);
    return room;
  }

  @Override
  public Room addCurrentUser(Long roomId) {
    var room = roomRepository.getReferenceById(roomId);
    if (CREATED != room.getStatus()) {
      throw new IllegalArgumentException("Room is already started!");
    }

    createParticipationWithCurrentUser(room);
    return room;
  }

  @Override
  public Room start(Long roomId) {
    var room = roomRepository.getReferenceById(roomId);
    if (room.getParticipations().size() < 3) {
      throw new IllegalArgumentException("Room doesn't have enough participients!");
    }
    room.setStatus(STARTED);
    return room;
  }

  @Override
  public Room setActiveWord(Long id, String word) {
    var room = roomRepository.getReferenceById(id);
    if (STARTED != room.getStatus()) {
      throw new IllegalAccessError("Room must be started to be set word!");
    }
    if (word == null || "".equals(word.trim())) {
      throw new IllegalAccessError("Word must not be blank!");
    }

    room.setActiveWord(word);
    return room;
  }

  @Override
  public Room addGuess(Long id, String guess) {
    if (guess == null || "".equals(guess.trim())) {
      throw new IllegalArgumentException("The guess cannot be null or blank!");
    }

    var room = roomRepository.getReferenceById(id);
    var userParticipation =
        room.getParticipations().stream()
            .filter(p -> p.getPercipient().getUsername().equals(getCurrentUsername()))
            .findFirst()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "No participation found for username: " + getCurrentUsername()));

    userParticipation.getGuesses().add(guess);
    return room;
  }

  @Override
  public Room finishRound(Long id) {
    var room = roomRepository.getReferenceById(id);
    if (STARTED != room.getStatus()) {
      throw new IllegalArgumentException("Cannot finish round for not started room!");
    }

    room.addScoresToParticipients();
    room.nextRound();
    return room;
  }

  private void createParticipationWithCurrentUser(Room room) {
    var currentUser = userService.getCurrentUser();

    var participation =
        RoomParticipation.builder().room(room).percipient(currentUser).score(0).build();
    participation = participationRepository.save(participation);
    room.getParticipations().add(participation);
  }
}
