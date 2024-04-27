package com.example.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.api.controller.NicknameController;
import com.example.api.dto.NicknameRequest;
import com.example.api.dto.NicknameResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class NicknameControllerTest {

  @Autowired
  private NicknameController nicknameController;

  @Test
  void testGenerateNicknameAppendsRandomCharsAndNumber() {
    // Given
    NicknameRequest request = new NicknameRequest();
    request.setName("John");

    // When
    NicknameResponse response = nicknameController.generateNickname(request);

    // Then
    String expectedStart = "John";
    assertTrue(response.getNickname().startsWith(expectedStart));
    assertEquals(8, response.getNickname().length());
  }

  @Test
  void testGenerateNicknameWithEmptyName() {
    // Given
    NicknameRequest request = new NicknameRequest();
    request.setName("");

    // When
    NicknameResponse response = nicknameController.generateNickname(request);

    // Then
    assertEquals(4, response.getNickname().length());
  }

  @Test
  void testGenerateNicknameWithExcessivelyLongName() {
    // Given
    String longName = "John".repeat(100);
    NicknameRequest request = new NicknameRequest();
    request.setName(longName);

    // When
    NicknameResponse response = nicknameController.generateNickname(request);

    // Then
    assertTrue(response.getNickname().startsWith(longName));
    assertTrue(response.getNickname().length() > longName.length());
  }

  @Test
  void testGenerateNicknameWithNullName() {
    // Given
    NicknameRequest request = new NicknameRequest();
    request.setName(null);

    // When
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> nicknameController.generateNickname(request));

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus(),
        "Status code should be BAD_REQUEST");
  }

  @Test
  void testGenerateNicknameWithSpecialCharsInName() {
    // Given
    NicknameRequest request = new NicknameRequest();
    request.setName("John$%^&");

    // When
    NicknameResponse response = nicknameController.generateNickname(request);

    // Then
    assertTrue(response.getNickname().startsWith("John$%^&"));
    assertTrue(response.getNickname().length() > request.getName().length());
  }
}
