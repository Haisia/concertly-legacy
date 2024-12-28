package com.concertly.concertly_legacy.commons.exceptions;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                @NonNull HttpHeaders headers,
                                                                @NonNull HttpStatusCode status,
                                                                @NonNull WebRequest request) {
    Map<String, String> validationErrors = new HashMap<>();
    List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

    validationErrorList.forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String validationMsg = error.getDefaultMessage();
      validationErrors.put(fieldName, validationMsg);
    });
    return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AlreadyExistException.class)
  public ResponseEntity<ErrorResponseDto> handleAlreadyExistException(AlreadyExistException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    return new ResponseEntity<>(errorResponseDto, status);
  }

  @ExceptionHandler(InvalidParameterException.class)
  public ResponseEntity<ErrorResponseDto> handleInvalidParameterException(InvalidParameterException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    return new ResponseEntity<>(errorResponseDto, status);
  }

  @ExceptionHandler(LoginFailException.class)
  public ResponseEntity<ErrorResponseDto> handleLoginFailException(LoginFailException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    return new ResponseEntity<>(errorResponseDto, status);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    return new ResponseEntity<>(errorResponseDto, status);
  }

  @ExceptionHandler(PermissionDeniedException.class)
  public ResponseEntity<ErrorResponseDto> handlePermissionDeniedException(PermissionDeniedException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    return new ResponseEntity<>(errorResponseDto, status);
  }

  @ExceptionHandler(UnableStatusException.class)
  public ResponseEntity<ErrorResponseDto> handleUnableStatusException(UnableStatusException exception, WebRequest webRequest) {
    HttpStatus status = resolveHttpStatus(exception);
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest, exception, status);
    return new ResponseEntity<>(errorResponseDto, status);
  }

  private HttpStatus resolveHttpStatus(Exception exception) {
    ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
    if (responseStatus != null) {
      return responseStatus.value();
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
