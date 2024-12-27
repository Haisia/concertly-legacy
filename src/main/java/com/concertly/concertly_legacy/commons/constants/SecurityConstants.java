package com.concertly.concertly_legacy.commons.constants;

public final class SecurityConstants {

  public static final Long EXPIRATION_TIME = 60 * 60 * 1000L;
  public static final String HEADER_PREFIX = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  private SecurityConstants() {
  }
}