package com.concertly.concertly_legacy.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistException extends RuntimeException {
  public AlreadyExistException(String existValue) {
    super(String.format("%s 는 이미 존재하는 값 입니다.", existValue));
  }
}
