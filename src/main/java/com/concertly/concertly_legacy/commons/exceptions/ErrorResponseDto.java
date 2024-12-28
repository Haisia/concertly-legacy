package com.concertly.concertly_legacy.commons.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {

  @Schema(description = "API 요청 경로", example = "/api/users")
  private String apiPath;

  @Schema(description = "HTTP 상태 코드", example = "404")
  private HttpStatus errorCode;

  @Schema(description = "오류 메시지", example = "User not found")
  private String errorMessage;

  @Schema(description = "오류 발생 시각", example = "2023-01-01T12:00:00")
  private LocalDateTime errorTime;

  public ErrorResponseDto(WebRequest webRequest, Exception exception, HttpStatus errorCode) {
    this.apiPath = webRequest.getDescription(false);
    this.errorCode = errorCode;
    this.errorMessage = exception.getMessage();
    this.errorTime = LocalDateTime.now();
  }

}