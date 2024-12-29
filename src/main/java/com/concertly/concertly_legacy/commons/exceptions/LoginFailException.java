package com.concertly.concertly_legacy.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class LoginFailException extends RuntimeException {
  public LoginFailException(String message) {
    super(message);
  }
}
