package com.concertly.concertly_legacy.commons.exceptions;

public class AlreadyExistException extends RuntimeException {
  public AlreadyExistException(String existValue) {
    super(String.format("%s 는 이미 존재하는 값 입니다.", existValue));
  }
}
