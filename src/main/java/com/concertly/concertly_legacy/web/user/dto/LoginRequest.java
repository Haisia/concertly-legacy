package com.concertly.concertly_legacy.web.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
  public String email;
  public String password;
}
