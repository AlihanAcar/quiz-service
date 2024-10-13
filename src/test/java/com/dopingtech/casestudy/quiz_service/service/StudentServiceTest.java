package com.dopingtech.casestudy.quiz_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private QuizRepository quizRepository;

  @Mock
  private QuizAssignmentRepository quizAssignmentRepository;

  @InjectMocks
  private StudentService studentService;

  private Student student;
  private StudentDTO studentDTO;

  @BeforeEach
  public void setUp() {
    student = new Student();
    student.setId(1L);
    student.setFirstName("John");
    student.setLastName("Doe");
    student.setNumber("12345");

    studentDTO = new StudentDTO();
    studentDTO.setFirstName("John");
    studentDTO.setLastName("Doe");
    studentDTO.setNumber("12345");
  }

  @Test
  public void findAll_shouldReturnStudentInfoDTOs() {
    List<Student> students = Collections.singletonList(student);
    when(studentRepository.findAll()).thenReturn(students);

    List<StudentInfoDTO> result = studentService.findAll();

    assertEquals(students.get(0).getId(), result.get(0).getId());
  }


  @Test
  public void findAll_shouldCallFindAllMethodOnce() {
    List<Student> students = Collections.singletonList(student);
    when(studentRepository.findAll()).thenReturn(students);

    studentService.findAll();

    verify(studentRepository, times(1)).findAll();
  }

  @Test
  public void findById_shouldReturnStudent() {
    when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

    Student result = studentService.findById(1L);

    assertEquals(student, result);
  }

  @Test
  public void findById_shouldCallFindByIdMethodOnce() {
    when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

    studentService.findById(1L);

    verify(studentRepository, times(1)).findById(1L);
  }

  @Test
  public void findById_shouldThrowStudentNotFoundException() {
    when(studentRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> studentService.findById(1L));
  }

  @Test
  public void findQuizAssignmentsByStudentId_shouldReturnQuizAssignments() {
    QuizAssignment assignment = new QuizAssignment();
    assignment.setId(1L);
    assignment.setStudent(student);

    List<QuizAssignment> quizAssignments = new ArrayList<>();
    quizAssignments.add(assignment);

    when(studentRepository.existsById(student.getId())).thenReturn(true);
    when(quizAssignmentRepository.findByStudentId(1L)).thenReturn(List.of(assignment));

    List<QuizAssignment> result = studentService.findQuizAssignmentsByStudentId(1L);

    assertEquals(quizAssignments, result);
  }

  @Test
  public void findQuizAssignmentsByStudentId_shouldThrowStudentNotFoundException() {
    when(studentRepository.existsById(1L)).thenReturn(false);

    assertThrows(StudentNotFoundException.class, () -> {
      studentService.findQuizAssignmentsByStudentId(1L);
    });
  }

  @Test
  public void findQuizzesByStudentId_shouldReturnQuizzes() {
    Quiz quiz = new Quiz();
    quiz.setId(1L);
    quiz.setName("Sample Quiz");

    when(studentRepository.existsById(student.getId())).thenReturn(true);
    when(quizRepository.findByQuizAssignmentsStudentId(1L)).thenReturn(List.of(quiz));

    List<QuizInfoDTO> quizzes = studentService.findQuizzesByStudentId(1L);

    assertEquals(quiz.getId(), quizzes.get(0).getId());
  }

  @Test
  public void findQuizzesByStudentId_shouldCallFindByQuizAssignmentsStudentIdethodOnce() {
    Quiz quiz = new Quiz();
    quiz.setId(1L);
    quiz.setName("Sample Quiz");

    when(studentRepository.existsById(student.getId())).thenReturn(true);
    when(quizRepository.findByQuizAssignmentsStudentId(1L)).thenReturn(List.of(quiz));

    studentService.findQuizzesByStudentId(1L);

    verify(quizRepository, times(1)).findByQuizAssignmentsStudentId(1L);
  }

  @Test
  public void findQuizzesByStudentId_shouldThrowStudentNotFoundException() {
    Long studentId = 1L;

    when(studentRepository.existsById(studentId)).thenReturn(false);

    assertThrows(StudentNotFoundException.class, () -> {
      studentService.findQuizzesByStudentId(studentId);
    });
  }

  @Test
  public void getCompletedQuizzesByStudentId_shouldReturnCompletedQuizzes() {
    Quiz quiz = new Quiz();
    quiz.setId(1L);
    quiz.setName("Sample Quiz");

    QuizAssignment assignment = new QuizAssignment();
    assignment.setId(1L);
    assignment.setStatus(QuizAssignmentStatus.COMPLETED);
    assignment.setQuiz(quiz);

    List<QuizAssignment> quizAssignments = new ArrayList<>();
    quizAssignments.add(assignment);

    when(studentRepository.existsById(1L)).thenReturn(true);
    when(quizAssignmentRepository.findByStudentId(1L))
        .thenReturn(Collections.singletonList(assignment));

    List<CompletedQuizDTO> result = studentService.getCompletedQuizzesByStudentId(1L);

    assertEquals(quizAssignments.get(0).getQuiz().getName(), result.get(0).getQuizName());
  }

  @Test
  public void getCompletedQuizzesByStudentId_shouldThrowStudentNotFoundException() {
    when(studentRepository.existsById(1L)).thenReturn(false);

    assertThrows(StudentNotFoundException.class, () -> {
      studentService.getCompletedQuizzesByStudentId(1L);
    });
  }

  @Test
  public void createStudent_shouldReturnCreatedStudent() {
    when(studentRepository.save(any(Student.class))).thenReturn(student);

    Student createdStudent = studentService.createStudent(studentDTO);

    assertEquals(student.getFirstName(), createdStudent.getFirstName());
  }

  @Test
  public void createStudent_shouldCallSavedMethodOnce() {
    when(studentRepository.save(any(Student.class))).thenReturn(student);

    studentService.createStudent(studentDTO);

    verify(studentRepository, times(1)).save(any(Student.class));
  }

  @Test
  public void updateStudent_shouldReturnUpdatedStudent() {
    when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
    when(studentRepository.save(any(Student.class))).thenReturn(student);

    Student updatedStudent = new Student();
    updatedStudent.setId(1L);
    updatedStudent.setFirstName("John");
    updatedStudent.setLastName("Doe");
    updatedStudent.setNumber("12345");

    Student updateStudent = studentService.updateStudent(1L, studentDTO);

    assertEquals(student.getFirstName(), updateStudent.getFirstName());
  }

  @Test
  public void updateStudent_shouldCallSavedMethodOnce() {
    when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
    when(studentRepository.save(any(Student.class))).thenReturn(student);

    Student updatedStudent = new Student();
    updatedStudent.setId(1L);
    updatedStudent.setFirstName("John");
    updatedStudent.setLastName("Doe");
    updatedStudent.setNumber("12345");

    studentService.updateStudent(1L, studentDTO);

    verify(studentRepository, times(1)).save(any(Student.class));
  }

  @Test
  public void updateStudent_shouldThrowStudentNotFoundException() {
    when(studentRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class,
        () -> studentService.updateStudent(1L, studentDTO));
  }

  @Test
  public void deleteStudent_shouldCallDeleteByIdMethodOnce() {
    when(studentRepository.existsById(1L)).thenReturn(true);
    doNothing().when(studentRepository).deleteById(1L);

    studentService.deleteStudent(1L);

    verify(studentRepository).deleteById(1L);
  }

  @Test
  public void deleteStudent_shouldThrowStudentNotFoundException() {
    when(studentRepository.existsById(1L)).thenReturn(false);

    assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(1L));
  }
}