package com.concertly.concertly_legacy.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UnableStatusException extends RuntimeException {
  public UnableStatusException(String message) {
    super(message);
  }
}
