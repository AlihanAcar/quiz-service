package com.dopingtech.casestudy.quiz_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletedQuizDTO {
  private String quizName;
  private int correctAnswerCount;
  private double score;
}
