package com.concertly.concertly_legacy.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
  public NotFoundException(String resourceName, String fieldName, String fieldValue) {
    super(String.format("%s 를 찾을 수 없습니다. 파라미터 : %s = '%s'", resourceName, fieldName, fieldValue));
  }
}
