package com.concertly.concertly_legacy.commons.exceptions;

public class PermissionDeniedException extends RuntimeException{
  public PermissionDeniedException() {
    super("권한이 없습니다.");
  }
}
