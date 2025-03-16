package com.github.drawmything.word.service;

import static java.util.Collections.shuffle;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Random;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WordServiceImpl {

  List<String> words;
  Random random;

  public WordServiceImpl(@Value("${words}") String words) {
    this.words = List.of(words.split(","));
    this.random = new Random();
  }

  public List<String> getRandomWords(int count) {
    if (count <= 0) {
      throw new IllegalArgumentException("Count must be atleast 1!");
    }
    shuffle(words, random);
    return words.subList(0, Math.min(count, words.size()));
  }
}
