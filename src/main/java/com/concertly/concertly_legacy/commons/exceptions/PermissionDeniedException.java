package com.concertly.concertly_legacy.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException{
  public PermissionDeniedException() {
    super("권한이 없습니다.");
  }
}
