package com.dopingtech.casestudy.quiz_service.exception;

import org.springframework.http.HttpStatus;

public class QuizAssignmentNotFoundException extends CustomException {
  public QuizAssignmentNotFoundException(Long quizAssignmentId) {
    super(ErrorCode.QUIZ_ASSIGNMENT_NOT_FOUND, HttpStatus.NOT_FOUND,
        String.format("Quiz assignment with id %d not found", quizAssignmentId));
  }
}
