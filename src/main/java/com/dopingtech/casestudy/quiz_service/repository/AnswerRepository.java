package com.dopingtech.casestudy.quiz_service.repository;

import com.dopingtech.casestudy.quiz_service.model.entity.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  List<Answer> findByQuizAssignmentId(Long quizAssignmentId);

  Answer findByQuizAssignmentIdAndQuestionId(Long quizAssignmentId, Long questionId);
}
