package com.concertly.concertly_legacy.web.user.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
  public String email;
  public String password;
}
