package com.dopingtech.casestudy.quiz_service.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Entity
@Table(name = "option", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"question_id", "letter"}),
    @UniqueConstraint(columnNames = {"question_id", "text"})
})
public class Option {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @NotBlank(message = "Option text is required")
  private String text;

  @NotBlank(message = "Option letter is required")
  @Pattern(regexp = "[A-E]", message = "Option letter must be one of A, B, C, D, E")
  private String letter;
}
