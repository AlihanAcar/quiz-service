package com.dopingtech.casestudy.quiz_service.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "answers")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "quiz_assignment_id", nullable = false)
  private QuizAssignment quizAssignment;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  @Pattern(regexp = "^[A-E]$|^$", message = "Selected option must be one of: A, B, C, D, E or left blank")
  private String selectedOption;

  @NotNull
  private boolean isCorrect;
}
