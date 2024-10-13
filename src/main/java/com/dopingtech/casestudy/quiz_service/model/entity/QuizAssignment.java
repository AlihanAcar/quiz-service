package com.dopingtech.casestudy.quiz_service.model.entity;

import com.dopingtech.casestudy.quiz_service.model.enums.QuizAssignmentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
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
@Entity
@Table(name = "quiz_assignments", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id",
    "quiz_id"}))
public class QuizAssignment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "quiz_id", nullable = false)
  private Quiz quiz;

  @Enumerated(EnumType.STRING)
  private QuizAssignmentStatus status;

  @NotNull
  private int correctAnswerCount;

  @NotNull
  private double score;

  @JsonManagedReference
  @OneToMany(mappedBy = "quizAssignment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Answer> answers;
}
