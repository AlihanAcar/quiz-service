package com.dopingtech.casestudy.quiz_service.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "question", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"quiz_id", "text"})
})
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Question text is required")
  private String text;

  @JsonManagedReference
  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<Option> options;

  @NotBlank(message = "Correct answer is required")
  @Pattern(regexp = "[A-E]", message = "Correct answer must be one of A, B, C, D, E")
  private String correctAnswer;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "quiz_id", nullable = false)
  private Quiz quiz;
}
