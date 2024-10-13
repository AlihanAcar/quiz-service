package com.dopingtech.casestudy.quiz_service.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
    log.error("Message : {}", e.getMessage(), e);
    Map<String, Object> response = new HashMap<>();
    response.put("error", e.getErrorCode().getMessage());
    response.put("code", e.getErrorCode().getCode());
    return ResponseEntity.status(e.getHttpStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException e) {
    log.error("Message : {}", e.getMessage(), e);
    Map<String, Object> response = new HashMap<>();
    Map<String, Object> errors = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });
    response.put("code", ErrorCode.VALIDATION_ERROR.getCode());
    response.put("errors", errors);
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
      DataIntegrityViolationException e) {
    log.error("Data integrity violation occurred: {}", e.getMessage(), e);
    Map<String, Object> response = new HashMap<>();
    response.put("code", ErrorCode.DATA_INTEGRITY_VIOLATION.getCode());
    response.put("error", e.getRootCause().getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409 Conflict
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
    log.error("An unexpected error occurred : {}", e.getMessage(), e);
    Map<String, Object> response = new HashMap<>();
    response.put("error", "An unexpected error occurred.");
    response.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
