package com.concertly.concertly_legacy.commons.dto;

import com.concertly.concertly_legacy.commons.enums.BaseResponseStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import static com.concertly.concertly_legacy.commons.enums.BaseResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"status", "result"})
public class BaseResponse<T> {
  private final BaseResponseStatus status;
  private T result;

  public BaseResponse(T result) {
    this.status = SUCCESS;
    this.result = result;
  }

  public BaseResponse(BaseResponseStatus status) {
    this.status = status;
  }

  public BaseResponse(BaseResponseStatus status, T result) {
    this.status = status;
    this.result = result;
  }
}

