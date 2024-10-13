package com.dopingtech.casestudy.quiz_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDTO {

  @NotBlank(message = "Quiz name is required")
  private String name;
}
