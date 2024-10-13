package com.dopingtech.casestudy.quiz_service.repository;

import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

  List<Question> findByQuizId(Long quizId);
}
