package com.dopingtech.casestudy.quiz_service.exception;

import org.springframework.http.HttpStatus;

public class QuizCompletedAlreadyException extends CustomException {
  public QuizCompletedAlreadyException(Long quizAssignmentId) {
    super(ErrorCode.QUIZ_COMPLETED_ALREADY, HttpStatus.BAD_REQUEST,
        String.format("Quiz with Quiz Assignment id %d has already been completed", quizAssignmentId));
  }
}
