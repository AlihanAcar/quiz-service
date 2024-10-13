package com.dopingtech.casestudy.quiz_service.exception;

import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends CustomException {
  public StudentNotFoundException(Long studentId) {
    super(ErrorCode.STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND,
        String.format("Student with id %d not found", studentId));
  }
}
