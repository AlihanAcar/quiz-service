package com.dopingtech.casestudy.quiz_service.model.dto;

import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionWithoutAnswerDTO {
  private Long id;
  private String text;
  private List<Option> options;
  private Long quizId;
}

