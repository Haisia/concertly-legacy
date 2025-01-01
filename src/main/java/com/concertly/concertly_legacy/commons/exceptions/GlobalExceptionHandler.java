package com.concertly.concertly_legacy.commons.exceptions;

import com.concertly.concertly_legacy.commons.dto.BaseResponse;
import com.concertly.concertly_legacy.commons.enums.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler  {

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<?> handleRuntimeException(RuntimeException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    log.error(exception.getMessage(), exception);
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.ERROR, errorResponseDto));
  }

  @ExceptionHandler(AlreadyExistException.class)
  protected ResponseEntity<BaseResponse<ErrorResponseDto>> handleAlreadyExistException(AlreadyExistException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    log.info(exception.getMessage());
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.FAIL, errorResponseDto));
  }

  @ExceptionHandler(InvalidParameterException.class)
  protected ResponseEntity<BaseResponse<ErrorResponseDto>> handleInvalidParameterException(InvalidParameterException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    log.info(exception.getMessage());
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.FAIL, errorResponseDto));
  }

  @ExceptionHandler(LoginFailException.class)
  protected ResponseEntity<BaseResponse<ErrorResponseDto>> handleLoginFailException(LoginFailException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    log.info(exception.getMessage());
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.FAIL, errorResponseDto));
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<BaseResponse<ErrorResponseDto>> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    log.info(exception.getMessage());
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.FAIL, errorResponseDto));
  }

  @ExceptionHandler(PermissionDeniedException.class)
  protected ResponseEntity<BaseResponse<ErrorResponseDto>> handlePermissionDeniedException(PermissionDeniedException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    log.info(exception.getMessage());
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.FAIL, errorResponseDto));
  }

  @ExceptionHandler(UnableStatusException.class)
  protected ResponseEntity<BaseResponse<ErrorResponseDto>> handleUnableStatusException(UnableStatusException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    log.info(exception.getMessage());
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.FAIL, errorResponseDto));
  }

  protected HttpStatus resolveHttpStatus(Exception exception) {
    ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
    if (responseStatus != null) {
      return responseStatus.value();
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
