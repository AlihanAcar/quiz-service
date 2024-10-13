package com.dopingtech.casestudy.quiz_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.StudentNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.CompletedQuizDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.model.entity.Student;
import com.dopingtech.casestudy.quiz_service.service.StudentService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

  @Mock
  private StudentService studentService;

  @InjectMocks
  private StudentController studentController;

  private Student student;
  private StudentDTO studentDTO;
  private Quiz quiz;

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

    quiz = new Quiz();
    quiz.setId(1L);
    quiz.setName("Sample Quiz");
  }

  @Test
  public void getAllStudents_shouldReturnOK() {
    List<StudentInfoDTO> students =
        Collections.singletonList(new StudentInfoDTO(1L, "John", "Doe", "12345"));
    when(studentService.findAll()).thenReturn(students);

    ResponseEntity<List<StudentInfoDTO>> response = studentController.getAllStudents();

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getAllStudents_shouldReturnStudents() {
    List<StudentInfoDTO> students =
        Collections.singletonList(new StudentInfoDTO(1L, "John", "Doe", "12345"));
    when(studentService.findAll()).thenReturn(students);

    ResponseEntity<List<StudentInfoDTO>> response = studentController.getAllStudents();

    assertEquals(students, response.getBody());
  }

  @Test
  public void getStudentById_shouldReturnOK() {
    when(studentService.findById(1L)).thenReturn(student);

    ResponseEntity<Student> response = studentController.getStudentById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getStudentById_shouldReturnStudent() {
    when(studentService.findById(1L)).thenReturn(student);

    ResponseEntity<Student> response = studentController.getStudentById(1L);

    assertEquals(student, response.getBody());
  }

  @Test
  public void getStudentById_shouldThrowStudentNotFoundException() {
    when(studentService.findById(1L)).thenThrow(new StudentNotFoundException(1L));

    assertThrows(StudentNotFoundException.class, () -> studentController.getStudentById(1L));
  }

  @Test
  public void getQuizzesByStudentId_shouldReturnOK() {
    when(studentService.findQuizzesByStudentId(1L)).thenReturn(List.of(new QuizInfoDTO()));

    ResponseEntity<List<QuizInfoDTO>> response = studentController.getQuizzesByStudentId(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuizzesByStudentId_shouldReturnQuizzes() {
    QuizInfoDTO quizInfoDTO = new QuizInfoDTO();
    quizInfoDTO.setId(1L);
    quizInfoDTO.setName("Sample Quiz");

    when(studentService.findQuizzesByStudentId(1L)).thenReturn(List.of(quizInfoDTO));

    ResponseEntity<List<QuizInfoDTO>> response = studentController.getQuizzesByStudentId(1L);


    assertEquals(quizInfoDTO.getId(), response.getBody().get(0).getId());
  }

  @Test
  public void getQuizAssignmentsByStudentId_shouldReturnOK() {
    QuizAssignment quizAssignment = new QuizAssignment();
    quizAssignment.setId(1L);
    quizAssignment.setStudent(student);
    quizAssignment.setQuiz(quiz);

    when(studentService.findQuizAssignmentsByStudentId(1L)).thenReturn(List.of(quizAssignment));

    ResponseEntity<List<QuizAssignment>> response =
        studentController.getQuizAssignmentsByStudentId(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuizAssignmentsByStudentId_shouldReturnQuizAssignments() {
    QuizAssignment quizAssignment = new QuizAssignment();
    quizAssignment.setId(1L);
    quizAssignment.setStudent(student);
    quizAssignment.setQuiz(quiz);

    when(studentService.findQuizAssignmentsByStudentId(1L)).thenReturn(List.of(quizAssignment));

    ResponseEntity<List<QuizAssignment>> response =
        studentController.getQuizAssignmentsByStudentId(1L);

    assertEquals(List.of(quizAssignment), response.getBody());
  }

  @Test
  public void getQuizAssignmentsByStudentId_shouldThrowNotFoundException() {
    when(studentService.findQuizAssignmentsByStudentId(1L)).thenThrow(
        new StudentNotFoundException(1L));

    assertThrows(StudentNotFoundException.class,
        () -> studentController.getQuizAssignmentsByStudentId(1L));
  }

  @Test
  public void getCompletedQuizzes_shouldReturnOK() {
    List<CompletedQuizDTO> completedQuizzes =
        Collections.singletonList(new CompletedQuizDTO("Quiz", 1, 100.0));
    when(studentService.getCompletedQuizzesByStudentId(1L)).thenReturn(completedQuizzes);

    ResponseEntity<List<CompletedQuizDTO>> response =
        studentController.getCompletedQuizzesByStudentId(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getCompletedQuizzes_shouldReturnCompletedQuizzes() {
    List<CompletedQuizDTO> completedQuizzes =
        Collections.singletonList(new CompletedQuizDTO("Quiz", 1, 100.0));
    when(studentService.getCompletedQuizzesByStudentId(1L)).thenReturn(completedQuizzes);

    ResponseEntity<List<CompletedQuizDTO>> response =
        studentController.getCompletedQuizzesByStudentId(1L);

    assertEquals(completedQuizzes, response.getBody());
  }

  @Test
  public void getCompletedQuizzes_shouldThrowStudentNotFoundException() {
    when(studentService.getCompletedQuizzesByStudentId(1L)).thenThrow(
        new StudentNotFoundException(1L));

    assertThrows(StudentNotFoundException.class, () -> {
      studentController.getCompletedQuizzesByStudentId(1L);
    });
  }

  @Test
  public void createStudent_shouldReturnCreated() {
    when(studentService.createStudent(any(StudentDTO.class))).thenReturn(student);

    ResponseEntity<Student> response = studentController.createStudent(studentDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void createStudent_shouldReturnCreatedStudent() {
    when(studentService.createStudent(any(StudentDTO.class))).thenReturn(student);

    ResponseEntity<Student> response = studentController.createStudent(studentDTO);

    assertEquals(student, response.getBody());
  }

  @Test
  public void updateStudent_shouldReturnOK() {
    when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(student);

    ResponseEntity<Student> response = studentController.updateStudent(1L, studentDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void updateStudent_shouldReturnUpdatedStudent() {
    when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(student);

    ResponseEntity<Student> response = studentController.updateStudent(1L, studentDTO);

    assertEquals(student, response.getBody());
  }

  @Test
  public void updateStudent_shouldThrowStudentNotFoundException() {
    when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenThrow(
        new StudentNotFoundException(1L));

    assertThrows(StudentNotFoundException.class,
        () -> studentController.updateStudent(1L, studentDTO));
  }

  @Test
  public void deleteStudent_shouldReturnOK() {
    doNothing().when(studentService).deleteStudent(1L);

    ResponseEntity<String> response = studentController.deleteStudent(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void deleteStudent_shouldReturnSuccessMessage() {
    doNothing().when(studentService).deleteStudent(1L);

    ResponseEntity<String> response = studentController.deleteStudent(1L);

    assertEquals("Student deleted successfully.", response.getBody());
  }

  @Test
  public void deleteStudent_shouldThrowStudentNotFoundException() {
    doThrow(new StudentNotFoundException(1L)).when(studentService).deleteStudent(1L);

    assertThrows(StudentNotFoundException.class, () -> studentController.deleteStudent(1L));
  }
}