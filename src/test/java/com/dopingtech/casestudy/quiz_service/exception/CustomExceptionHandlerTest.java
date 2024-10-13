package com.dopingtech.casestudy.quiz_service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

  @InjectMocks
  private CustomExceptionHandler customExceptionHandler;

  @Mock
  private MethodArgumentNotValidException methodArgumentNotValidException;

  @Mock
  private DataIntegrityViolationException dataIntegrityViolationException;

  @Mock
  private org.springframework.validation.BindingResult bindingResult;

  @Test
  public void handleCustomException_shouldReturnCustomExceptionHttpStatus() {
    CustomException exception = new StudentNotFoundException(1L);

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleCustomException(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void handleCustomException_shouldReturnCustomErrorResponse() {
    CustomException exception = new StudentNotFoundException(1L);

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleCustomException(exception);

    Map<String, Object> expectedResponse = new HashMap<>();
    expectedResponse.put("error", "Student not found");
    expectedResponse.put("code", 1001);
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  public void handleValidationExceptions_shouldReturnBadRequest() {
    List<FieldError> fieldErrors = new ArrayList<>();

    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void handleValidationExceptions_shouldReturnValidationError() {
    List<FieldError> fieldErrors = new ArrayList<>();

    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

    Map<String, Object> responseBody = response.getBody();
    assertEquals(ErrorCode.VALIDATION_ERROR.getCode(), responseBody.get("code"));
  }

  @Test
  public void handleValidationExceptions_shouldReturnErrors() {
    List<FieldError> fieldErrors = new ArrayList<>();
    fieldErrors.add(new FieldError("quizAssignmentDTO", "studentId", "Student id is required."));

    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

    Map<String, Object> responseBody = response.getBody();
    Map<String, String> errors = (Map<String, String>) responseBody.get("errors");
    assertEquals("Student id is required.", errors.get("studentId"));
  }

  @Test
  public void handleDataIntegrityViolationException_shouldReturnConflict() {
    when(dataIntegrityViolationException.getRootCause()).thenReturn(new Exception());

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleDataIntegrityViolationException(
            dataIntegrityViolationException);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  public void handleDataIntegrityViolationException_shouldReturnDataIntegrityViolationErrorCode() {
    when(dataIntegrityViolationException.getRootCause()).thenReturn(new Exception());

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleDataIntegrityViolationException(
            dataIntegrityViolationException);

    Map<String, Object> responseBody = response.getBody();
    assertEquals(ErrorCode.DATA_INTEGRITY_VIOLATION.getCode(), responseBody.get("code"));
  }

  @Test
  public void handleGenericException_shouldReturnInternalServerError() {
    Exception exception = new Exception("Generic error");

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleGenericException(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  public void handleGenericException_shouldReturnGenericErrorResponse() {
    Exception exception = new Exception("Generic error");

    ResponseEntity<Map<String, Object>> response =
        customExceptionHandler.handleGenericException(exception);

    Map<String, Object> expectedResponse = new HashMap<>();
    expectedResponse.put("error", "An unexpected error occurred.");
    expectedResponse.put("message", "Generic error");
    assertEquals(expectedResponse, response.getBody());
  }
}