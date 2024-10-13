package com.dopingtech.casestudy.quiz_service.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizAssignmentDTO {

  @NotNull(message = "Student id cannot be null")
  private Long studentId;

  @NotNull(message = "Quiz id cannot be null")
  private Long quizId;
}
