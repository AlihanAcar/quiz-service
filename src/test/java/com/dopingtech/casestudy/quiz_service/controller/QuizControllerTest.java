package com.dopingtech.casestudy.quiz_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.QuizNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.model.entity.Student;
import com.dopingtech.casestudy.quiz_service.service.QuizService;
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
public class QuizControllerTest {

  @Mock
  private QuizService quizService;

  @InjectMocks
  private QuizController quizController;

  private Quiz quiz;
  private QuizDTO quizDTO;

  @BeforeEach
  public void setUp() {
    quiz = new Quiz();
    quiz.setId(1L);
    quiz.setName("Sample Quiz");

    quizDTO = new QuizDTO();
    quizDTO.setName("Sample Quiz");
  }

  @Test
  public void getAllQuizzes_shouldReturnOK() {
    List<QuizInfoDTO> quizInfoDTOs =
        Collections.singletonList(new QuizInfoDTO(quiz.getId(), quiz.getName()));
    when(quizService.findAll()).thenReturn(quizInfoDTOs);

    ResponseEntity<List<QuizInfoDTO>> response = quizController.getAllQuizzes();

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getAllQuizzes_shouldReturnQuizzes() {
    List<QuizInfoDTO> quizInfoDTOs =
        Collections.singletonList(new QuizInfoDTO(quiz.getId(), quiz.getName()));
    when(quizService.findAll()).thenReturn(quizInfoDTOs);

    ResponseEntity<List<QuizInfoDTO>> response = quizController.getAllQuizzes();

    assertEquals(quizInfoDTOs, response.getBody());
  }

  @Test
  public void getQuizById_shouldReturnOK() {
    when(quizService.findById(1L)).thenReturn(quiz);

    ResponseEntity<Quiz> response = quizController.getQuizById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuizById_shouldReturnQuiz() {
    when(quizService.findById(1L)).thenReturn(quiz);

    ResponseEntity<Quiz> response = quizController.getQuizById(1L);

    assertEquals(quiz, response.getBody());
  }

  @Test
  public void getQuizById_shouldThrowQuizNotFoundException() {
    when(quizService.findById(1L)).thenThrow(new QuizNotFoundException(1L));

    assertThrows(QuizNotFoundException.class, () -> quizController.getQuizById(1L));
  }

  @Test
  public void getQuizAssignmentsByQuizId_shouldReturnOK() {
    List<QuizAssignment> quizAssignments = Collections.singletonList(new QuizAssignment());
    when(quizService.findQuizAssignmentsByQuizId(1L)).thenReturn(quizAssignments);

    ResponseEntity<List<QuizAssignment>> response = quizController.getQuizAssignmentsByQuizId(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuizAssignmentsByQuizId_shouldReturnQuizAssignments() {
    List<QuizAssignment> quizAssignments = Collections.singletonList(new QuizAssignment());
    when(quizService.findQuizAssignmentsByQuizId(1L)).thenReturn(quizAssignments);

    ResponseEntity<List<QuizAssignment>> response = quizController.getQuizAssignmentsByQuizId(1L);

    assertEquals(quizAssignments, response.getBody());
  }

  @Test
  public void getQuizAssignmentsByQuizId_shouldThrowQuizNotFoundException() {
    when(quizService.findQuizAssignmentsByQuizId(1L)).thenThrow(new QuizNotFoundException(1L));

    assertThrows(QuizNotFoundException.class, () -> quizController.getQuizAssignmentsByQuizId(1L));
  }

  @Test
  public void getStudentsByQuizId_shouldReturnOK() {
    List<StudentInfoDTO> students = Collections.singletonList(new StudentInfoDTO());
    when(quizService.findStudentsByQuizId(1L)).thenReturn(students);

    ResponseEntity<List<StudentInfoDTO>> response = quizController.getStudentsByQuizId(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getStudentsByQuizId_shouldReturnStudents() {
    List<StudentInfoDTO> students = Collections.singletonList(new StudentInfoDTO());
    when(quizService.findStudentsByQuizId(1L)).thenReturn(students);

    ResponseEntity<List<StudentInfoDTO>> response = quizController.getStudentsByQuizId(1L);

    assertEquals(students, response.getBody());
  }

  @Test
  public void getStudentsByQuizId_shouldThrowQuizNotFoundException() {
    when(quizService.findStudentsByQuizId(1L)).thenThrow(new QuizNotFoundException(1L));

    assertThrows(QuizNotFoundException.class, () -> quizController.getStudentsByQuizId(1L));
  }

  @Test
  public void getQuestionsByQuizId_shouldReturnOK() {
    List<Question> questions = Collections.singletonList(new Question());
    when(quizService.findQuestionsByQuizId(1L)).thenReturn(questions);

    ResponseEntity<List<Question>> response = quizController.getQuestionsByQuizId(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuestionsByQuizId_shouldReturnQuestions() {
    List<Question> questions = Collections.singletonList(new Question());
    when(quizService.findQuestionsByQuizId(1L)).thenReturn(questions);

    ResponseEntity<List<Question>> response = quizController.getQuestionsByQuizId(1L);

    assertEquals(questions, response.getBody());
  }

  @Test
  public void getQuestionsByQuizId_shouldThrowQuizNotFoundException() {
    when(quizService.findQuestionsByQuizId(1L)).thenThrow(new QuizNotFoundException(1L));

    assertThrows(QuizNotFoundException.class, () -> quizController.getQuestionsByQuizId(1L));
  }

  @Test
  public void createQuiz_shouldReturnCreated() {
    when(quizService.createQuiz(any(QuizDTO.class))).thenReturn(quiz);

    ResponseEntity<Quiz> response = quizController.createQuiz(quizDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void createQuiz_shouldReturnCreatedQuiz() {
    when(quizService.createQuiz(any(QuizDTO.class))).thenReturn(quiz);

    ResponseEntity<Quiz> response = quizController.createQuiz(quizDTO);

    assertEquals(quiz, response.getBody());
  }

  @Test
  public void updateQuiz_shouldReturnOK() {
    when(quizService.updateQuiz(eq(1L), any(QuizDTO.class))).thenReturn(quiz);

    ResponseEntity<Quiz> response = quizController.updateQuiz(1L, quizDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void updateQuiz_shouldReturnUpdatedQuiz() {
    when(quizService.updateQuiz(eq(1L), any(QuizDTO.class))).thenReturn(quiz);

    ResponseEntity<Quiz> response = quizController.updateQuiz(1L, quizDTO);

    assertEquals(quiz, response.getBody());
  }

  @Test
  public void updateQuiz_shouldThrowNotFoundException() {
    when(quizService.updateQuiz(eq(1L), any(QuizDTO.class))).thenThrow(
        new QuizNotFoundException(1L));

    assertThrows(QuizNotFoundException.class, () -> quizController.updateQuiz(1L, quizDTO));
  }

  @Test
  public void deleteQuiz_shouldReturnsOK() {
    doNothing().when(quizService).deleteQuiz(1L);

    ResponseEntity<String> response = quizController.deleteQuiz(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void deleteQuiz_shouldReturnsSuccessMessage() {
    doNothing().when(quizService).deleteQuiz(1L);

    ResponseEntity<String> response = quizController.deleteQuiz(1L);

    assertEquals("Quiz deleted successfully.", response.getBody());
  }

  @Test
  public void deleteQuiz_shouldThrowQuizNotFoundException() {
    doThrow(new QuizNotFoundException(1L)).when(quizService).deleteQuiz(1L);

    assertThrows(QuizNotFoundException.class, () -> quizController.deleteQuiz(1L));
  }
}