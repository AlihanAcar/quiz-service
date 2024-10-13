package com.dopingtech.casestudy.quiz_service.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {

  @NotNull(message = "Quiz assignment id cannot be null")
  private Long quizAssignmentId;

  @NotNull(message = "Question id cannot be null")
  private Long questionId;

  @Pattern(regexp = "^[A-E]$|^$", message = "Selected option must be one of: A, B, C, D, E or left blank")
  private String selectedOption;
}
