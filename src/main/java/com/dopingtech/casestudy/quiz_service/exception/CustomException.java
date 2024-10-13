package com.dopingtech.casestudy.quiz_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
  private final ErrorCode errorCode;
  private final HttpStatus httpStatus;

  public CustomException(ErrorCode errorCode, HttpStatus httpStatus, String additionalMessage) {
    super(additionalMessage);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
  }
}
