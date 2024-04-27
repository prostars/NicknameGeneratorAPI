package com.example.api.controller;

import com.example.api.dto.NicknameRequest;
import com.example.api.dto.NicknameResponse;
import com.example.api.service.NicknameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class NicknameController {
  
  private static final Logger log = LoggerFactory.getLogger(NicknameController.class);
  private final NicknameService nicknameService;

  public NicknameController(NicknameService nicknameService) {
    this.nicknameService = nicknameService;
  }

  @PostMapping("/generateNickname")
  public NicknameResponse generateNickname(@RequestBody NicknameRequest request) {
    final String name = request.getName();
    try {
      final String nickname = nicknameService.generateNickname(name);
      final long simulateDelay = 5;
      log.info("Generated nickname: {} in {}ms", nickname, simulateDelay);
      Thread.sleep(simulateDelay);
      return new NicknameResponse(nickname);
    } catch (Exception e) {
      log.error("Error: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
