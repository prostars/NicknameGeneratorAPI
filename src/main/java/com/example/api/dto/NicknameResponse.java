package com.example.api.dto;

public class NicknameResponse {
  
  private final String nickname;

  public NicknameResponse(String nickname) {
    this.nickname = nickname;
  }

  public String getNickname() {
    return nickname;
  }
}
