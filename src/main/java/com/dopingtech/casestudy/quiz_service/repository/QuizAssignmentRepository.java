package com.dopingtech.casestudy.quiz_service.repository;

import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAssignmentRepository extends JpaRepository<QuizAssignment, Long> {

  List<QuizAssignment> findByStudentId(Long studentId);

  List<QuizAssignment> findByQuizId(Long quizId);
}
