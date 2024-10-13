package com.dopingtech.casestudy.quiz_service.repository;

import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

  List<Quiz> findByQuizAssignmentsStudentId(Long studentId);
}
