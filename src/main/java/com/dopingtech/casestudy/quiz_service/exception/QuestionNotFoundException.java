package com.dopingtech.casestudy.quiz_service.exception;

import org.springframework.http.HttpStatus;

public class QuestionNotFoundException extends CustomException {
  public QuestionNotFoundException(Long questionId) {
    super(ErrorCode.QUESTION_NOT_FOUND, HttpStatus.NOT_FOUND,
        String.format("Question with id %d not found", questionId));
  }
}
