package com.dopingtech.casestudy.quiz_service.exception;

import org.springframework.http.HttpStatus;

public class OptionNotFoundException extends CustomException {
  public OptionNotFoundException(Long optionId) {
    super(ErrorCode.OPTION_NOT_FOUND, HttpStatus.NOT_FOUND,
        String.format("Option with id %d not found", optionId));
  }
}
