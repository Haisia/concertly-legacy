package com.concertly.concertly_legacy.commons.exceptions;

public class LoginFailException extends RuntimeException {
  public LoginFailException(String email) {
    super(String.format("%s 유저가 로그인에 실패했습니다.", email));
  }
}
