package com.concertly.concertly_legacy.commons.exceptions;

public class LoginRequiredException extends RuntimeException {
  public LoginRequiredException(String requestUri) {
    super(String.format("로그인이 필요한 요청입니다. %s", requestUri));
  }
}
