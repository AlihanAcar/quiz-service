package com.dopingtech.casestudy.quiz_service.service;

import com.dopingtech.casestudy.quiz_service.exception.QuizNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.model.entity.Student;
import com.dopingtech.casestudy.quiz_service.repository.QuestionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuizAssignmentRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuizRepository;
import com.dopingtech.casestudy.quiz_service.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private QuizAssignmentRepository quizAssignmentRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Cacheable(value = "quizzes")
  public List<QuizInfoDTO> findAll() {
    List<Quiz> quizzes = quizRepository.findAll();
    return quizzes.stream()
        .map(quiz -> new QuizInfoDTO(quiz.getId(), quiz.getName()))
        .collect(Collectors.toList());
  }

  @Cacheable(value = "quizzes", key = "#id")
  public Quiz findById(Long id) {
    return quizRepository.findById(id)
        .orElseThrow(() -> new QuizNotFoundException(id));
  }

  public List<QuizAssignment> findQuizAssignmentsByQuizId(Long quizId) {
    if (!quizRepository.existsById(quizId)) {
      throw new QuizNotFoundException(quizId);
    }
    return quizAssignmentRepository.findByQuizId(quizId);
  }

  public List<StudentInfoDTO> findStudentsByQuizId(Long quizId) {
    if (!quizRepository.existsById(quizId)) {
      throw new QuizNotFoundException(quizId);
    }

    List<Student> students = studentRepository.findByQuizAssignmentsQuizId(quizId);
    return students.stream()
        .map(student -> new StudentInfoDTO(student.getId(), student.getFirstName(),
            student.getLastName(), student.getNumber()))
        .collect(Collectors.toList());
  }

  public List<Question> findQuestionsByQuizId(Long quizId) {
    if (!quizRepository.existsById(quizId)) {
      throw new QuizNotFoundException(quizId);
    }
    return questionRepository.findByQuizId(quizId);
  }

  @CacheEvict(value = "quizzes", allEntries = true)
  public Quiz createQuiz(QuizDTO quizDTO) {
    Quiz quiz = new Quiz();
    quiz.setName(quizDTO.getName());
    return quizRepository.save(quiz);
  }

  @CacheEvict(value = "quizzes", allEntries = true)
  public Quiz updateQuiz(Long id, QuizDTO quizDTO) {
    return quizRepository.findById(id)
        .map(existingQuiz -> {
          existingQuiz.setName(quizDTO.getName());
          return quizRepository.save(existingQuiz);
        })
        .orElseThrow(() -> new QuizNotFoundException(id));
  }

  @CacheEvict(value = "quizzes", allEntries = true)
  public void deleteQuiz(Long id) {
    if (!quizRepository.existsById(id)) {
      throw new QuizNotFoundException(id);
    }
    quizRepository.deleteById(id);
  }
}
