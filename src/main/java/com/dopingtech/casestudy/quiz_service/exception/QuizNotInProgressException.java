package com.dopingtech.casestudy.quiz_service.exception;

import org.springframework.http.HttpStatus;

public class QuizNotInProgressException extends CustomException {
  public QuizNotInProgressException(Long quizAssignmentId) {
    super(ErrorCode.QUIZ_NOT_IN_PROGRESS, HttpStatus.BAD_REQUEST,
        String.format("Quiz with Quiz Assignment id %d is not currently in progress.",
            quizAssignmentId));
  }
}
