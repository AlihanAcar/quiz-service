package com.dopingtech.casestudy.quiz_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {

  @NotBlank(message = "Question text is required")
  private String text;

  @NotNull(message = "Quiz id is required")
  private Long quizId;

  @NotBlank(message = "Correct answer is required")
  @Pattern(regexp = "[A-E]", message = "Correct answer must be one of A, B, C, D, E")
  private String correctAnswer;
}
