package com.dopingtech.casestudy.quiz_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String number;
}
