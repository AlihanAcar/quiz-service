package com.dopingtech.casestudy.quiz_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDTO {

  @NotNull(message = "Question id is required")
  private Long questionId;

  @NotBlank(message = "Option text is required")
  private String text;

  @NotBlank(message = "Option letter is required")
  @Pattern(regexp = "[A-E]", message = "Option letter must be one of A, B, C, D, E")
  private String letter;
}
