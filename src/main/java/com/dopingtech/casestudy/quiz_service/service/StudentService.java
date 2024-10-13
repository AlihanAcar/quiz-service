package com.dopingtech.casestudy.quiz_service.service;

import com.dopingtech.casestudy.quiz_service.exception.StudentNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.CompletedQuizDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.model.entity.Student;
import com.dopingtech.casestudy.quiz_service.model.enums.QuizAssignmentStatus;
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
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private QuizAssignmentRepository quizAssignmentRepository;

  @Cacheable(value = "students")
  public List<StudentInfoDTO> findAll() {
    return studentRepository.findAll().stream()
        .map(student -> new StudentInfoDTO(student.getId(), student.getFirstName(),
            student.getLastName(), student.getNumber()))
        .collect(Collectors.toList());
  }

  public Student findById(Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException(id));
  }

  public List<QuizAssignment> findQuizAssignmentsByStudentId(Long studentId) {
    if (!studentRepository.existsById(studentId)) {
      throw new StudentNotFoundException(studentId);
    }
    return quizAssignmentRepository.findByStudentId(studentId);
  }

  public List<QuizInfoDTO> findQuizzesByStudentId(Long studentId) {
    if (!studentRepository.existsById(studentId)) {
      throw new StudentNotFoundException(studentId);
    }

    List<Quiz> quizzes = quizRepository.findByQuizAssignmentsStudentId(studentId);
    return quizzes.stream()
        .map(quiz -> new QuizInfoDTO(quiz.getId(), quiz.getName()))
        .collect(Collectors.toList());
  }

  public List<CompletedQuizDTO> getCompletedQuizzesByStudentId(Long studentId) {
    if (!studentRepository.existsById(studentId)) {
      throw new StudentNotFoundException(studentId);
    }

    return quizAssignmentRepository.findByStudentId(studentId).stream()
        .filter(quizAssignment -> quizAssignment.getStatus() == QuizAssignmentStatus.COMPLETED)
        .map(quizAssignment -> new CompletedQuizDTO(
            quizAssignment.getQuiz().getName(),
            quizAssignment.getCorrectAnswerCount(),
            quizAssignment.getScore()))
        .collect(Collectors.toList());
  }

  @CacheEvict(value = "students", allEntries = true)
  public Student createStudent(StudentDTO studentDTO) {
    Student student = new Student();
    student.setFirstName(studentDTO.getFirstName());
    student.setLastName(studentDTO.getLastName());
    student.setNumber(studentDTO.getNumber());
    return studentRepository.save(student);
  }

  @CacheEvict(value = "students", allEntries = true)
  public Student updateStudent(Long id, StudentDTO studentDTO) {
    return studentRepository.findById(id)
        .map(existingStudent -> {
          existingStudent.setFirstName(studentDTO.getFirstName());
          existingStudent.setLastName(studentDTO.getLastName());
          existingStudent.setNumber(studentDTO.getNumber());
          return studentRepository.save(existingStudent);
        })
        .orElseThrow(() -> new StudentNotFoundException(id));
  }

  @CacheEvict(value = "students", allEntries = true)
  public void deleteStudent(Long id) {
    if (!studentRepository.existsById(id)) {
      throw new StudentNotFoundException(id);
    }
    studentRepository.deleteById(id);
  }
}
