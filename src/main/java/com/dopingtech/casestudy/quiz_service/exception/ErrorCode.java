package com.dopingtech.casestudy.quiz_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  STUDENT_NOT_FOUND(1001, "Student not found"),
  QUIZ_NOT_FOUND(1002, "Quiz not found"),
  QUESTION_NOT_FOUND(1003, "Question not found"),
  OPTION_NOT_FOUND(1004, "Option not found"),
  QUIZ_ASSIGNMENT_NOT_FOUND(1005, "Quiz assignment not found"),
  QUIZ_COMPLETED_ALREADY(1006, "The quiz has already been completed"),
  QUIZ_NOT_IN_PROGRESS(1007, "The quiz is not currently in progress"),
  VALIDATION_ERROR(1008, "Validation error"),
  DATA_INTEGRITY_VIOLATION(1009, "A data integrity violation occurred.");

  private final int code;
  private final String message;
}
