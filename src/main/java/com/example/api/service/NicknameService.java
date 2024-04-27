package com.example.api.service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class NicknameService {

  private static final int RANDOM_CHAR_COUNT = 3;

  public String generateNickname(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    return name + getRandomChars() + getRandomNumber();
  }

  private String getRandomChars() {
    Random random = new Random();
    return IntStream.range(0, RANDOM_CHAR_COUNT)
        .mapToObj(unused -> String.valueOf((char) ('A' + random.nextInt(26))))
        .collect(Collectors.joining());
  }

  private String getRandomNumber() {
    return Integer.toString(new Random().nextInt(10));
  }
}
