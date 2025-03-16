package com.github.drawmything.word.api;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.OK;

import com.github.drawmything.word.service.WordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WordApi {

  WordService wordService;

  @ResponseStatus(OK)
  @GetMapping("/random")
  public List<String> isUsernameTaken(@RequestBody Integer count) {
    return wordService.getRandomWords(count.intValue());
  }
}
