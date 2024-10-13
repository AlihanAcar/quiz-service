package com.dopingtech.casestudy.quiz_service.exception;

import org.springframework.http.HttpStatus;

public class QuizNotFoundException extends CustomException {
  public QuizNotFoundException(Long quizId) {
    super(ErrorCode.QUIZ_NOT_FOUND, HttpStatus.NOT_FOUND,
        String.format("Quiz with id %d not found", quizId));
  }
}
