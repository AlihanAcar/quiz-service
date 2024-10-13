package com.dopingtech.casestudy.quiz_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

  @NotBlank(message = "First name is required")
  private String firstName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  @NotBlank(message = "Student number is required")
  @Pattern(regexp = "\\d{5,10}", message = "Student number must be between 5 and 10 digits")
  private String number;
}
