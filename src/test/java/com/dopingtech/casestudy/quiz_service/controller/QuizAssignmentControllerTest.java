package com.dopingtech.casestudy.quiz_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.QuizAssignmentNotFoundException;
import com.dopingtech.casestudy.quiz_service.exception.QuizCompletedAlreadyException;
import com.dopingtech.casestudy.quiz_service.exception.QuizNotFoundException;
import com.dopingtech.casestudy.quiz_service.exception.QuizNotInProgressException;
import com.dopingtech.casestudy.quiz_service.exception.StudentNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.AnswerDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizAssignmentDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Answer;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.service.QuizAssignmentService;
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
public class QuizAssignmentControllerTest {

  @Mock
  private QuizAssignmentService quizAssignmentService;

  @InjectMocks
  private QuizAssignmentController quizAssignmentController;

  private AnswerDTO answerDTO;
  private QuizAssignmentDTO quizAssignmentDTO;

  @BeforeEach
  public void setUp() {
    answerDTO = new AnswerDTO();
    answerDTO.setQuizAssignmentId(1L);

    quizAssignmentDTO = new QuizAssignmentDTO();
    quizAssignmentDTO.setStudentId(1L);
    quizAssignmentDTO.setQuizId(1L);

  }

  @Test
  public void getAllQuizAssignments_ShouldReturnOK() {
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentService.findAll()).thenReturn(Collections.singletonList(assignment));

    ResponseEntity<List<QuizAssignment>> response =
        quizAssignmentController.getAllQuizAssignments();

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getAllQuizAssignments_ShouldReturnQuizAssignments() {
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentService.findAll()).thenReturn(Collections.singletonList(assignment));

    ResponseEntity<List<QuizAssignment>> response =
        quizAssignmentController.getAllQuizAssignments();

    assertEquals(Collections.singletonList(assignment), response.getBody());
  }

  @Test
  public void getAnswersByQuizAssignmentId_ShouldReturnOK() {
    Long quizAssignmentId = 1L;
    Answer answer = new Answer();
    when(quizAssignmentService.findAnswersByQuizAssignmentId(quizAssignmentId)).thenReturn(
        Collections.singletonList(answer));

    ResponseEntity<List<Answer>> response =
        quizAssignmentController.getAnswersByQuizAssignmentId(quizAssignmentId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getAnswersByQuizAssignmentId_ShouldReturnAnswers() {
    Long quizAssignmentId = 1L;
    Answer answer = new Answer();
    when(quizAssignmentService.findAnswersByQuizAssignmentId(quizAssignmentId)).thenReturn(
        Collections.singletonList(answer));

    ResponseEntity<List<Answer>> response =
        quizAssignmentController.getAnswersByQuizAssignmentId(quizAssignmentId);

    assertEquals(Collections.singletonList(answer), response.getBody());
  }

  @Test
  public void getAnswersByQuizAssignmentId_shouldThrowNotFoundException() {
    Long quizAssignmentId = 1L;
    when(quizAssignmentService.findAnswersByQuizAssignmentId(quizAssignmentId)).thenThrow(
        new QuizAssignmentNotFoundException(quizAssignmentId));

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentController.getAnswersByQuizAssignmentId(quizAssignmentId).getBody());
  }

  @Test
  public void getQuizAssignmentById_ShouldReturnOK() {
    Long id = 1L;
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentService.findById(id)).thenReturn(assignment);

    ResponseEntity<QuizAssignment> response = quizAssignmentController.getQuizAssignmentById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuizAssignmentById_ShouldReturnQuizAssignment() {
    Long id = 1L;
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentService.findById(id)).thenReturn(assignment);

    ResponseEntity<QuizAssignment> response = quizAssignmentController.getQuizAssignmentById(id);

    assertEquals(assignment, response.getBody());
  }

  @Test
  public void getQuizAssignmentById_shouldThrowNotFoundException() {
    Long id = 1L;
    when(quizAssignmentService.findById(id)).thenThrow(new QuizAssignmentNotFoundException(id));

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentController.getQuizAssignmentById(id).getBody());
  }

  @Test
  public void createQuizAssignment_ShouldReturnCreated() {
    QuizAssignment createdAssignment = new QuizAssignment();
    when(quizAssignmentService.createQuizAssignment(quizAssignmentDTO)).thenReturn(
        createdAssignment);

    ResponseEntity<QuizAssignment> response =
        quizAssignmentController.createQuizAssignment(quizAssignmentDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void createQuizAssignment_ShouldReturnCreatedAssignment() {
    QuizAssignment createdAssignment = new QuizAssignment();
    when(quizAssignmentService.createQuizAssignment(quizAssignmentDTO)).thenReturn(
        createdAssignment);

    ResponseEntity<QuizAssignment> response =
        quizAssignmentController.createQuizAssignment(quizAssignmentDTO);

    assertEquals(createdAssignment, response.getBody());
  }

  @Test
  public void createQuizAssignment_shouldThrowNotFoundException() {
    when(quizAssignmentService.createQuizAssignment(quizAssignmentDTO)).thenThrow(
        new StudentNotFoundException(quizAssignmentDTO.getStudentId()));

    assertThrows(StudentNotFoundException.class,
        () -> quizAssignmentController.createQuizAssignment(quizAssignmentDTO).getBody());
  }

  @Test
  public void createQuizAssignment_shouldThrowQuizNotFoundException() {
    when(quizAssignmentService.createQuizAssignment(quizAssignmentDTO)).thenThrow(
        new QuizNotFoundException(quizAssignmentDTO.getQuizId()));

    assertThrows(QuizNotFoundException.class,
        () -> quizAssignmentController.createQuizAssignment(quizAssignmentDTO).getBody());
  }

  @Test
  public void startQuiz_ShouldReturnOk() {
    Long quizAssignmentId = 1L;
    doNothing().when(quizAssignmentService).startQuiz(quizAssignmentId);

    ResponseEntity<String> response = quizAssignmentController.startQuiz(quizAssignmentId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void startQuiz_ShouldReturnSuccessMessage() {
    Long quizAssignmentId = 1L;
    doNothing().when(quizAssignmentService).startQuiz(quizAssignmentId);

    ResponseEntity<String> response = quizAssignmentController.startQuiz(quizAssignmentId);

    assertEquals("Quiz started successfully.", response.getBody());
  }

  @Test
  public void startQuiz_shouldThrowQuizAssignmentNotFoundException() {
    Long quizAssignmentId = 1L;
    doThrow(new QuizAssignmentNotFoundException(quizAssignmentId)).when(quizAssignmentService)
        .startQuiz(quizAssignmentId);

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentController.startQuiz(quizAssignmentId).getBody());
  }

  @Test
  public void startQuiz_shouldThrowQuizCompletedAlreadyException() {
    Long quizAssignmentId = 1L;
    doThrow(new QuizCompletedAlreadyException(quizAssignmentId)).when(quizAssignmentService)
        .startQuiz(quizAssignmentId);

    assertThrows(QuizCompletedAlreadyException.class,
        () -> quizAssignmentController.startQuiz(quizAssignmentId).getBody());
  }


  @Test
  public void answerQuestion_ShouldReturnOk() {
    doNothing().when(quizAssignmentService).answerQuestion(answerDTO);

    ResponseEntity<String> response = quizAssignmentController.answerQuestion(answerDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void answerQuestion_ShouldReturnSuccessMessage() {
    doNothing().when(quizAssignmentService).answerQuestion(answerDTO);

    ResponseEntity<String> response = quizAssignmentController.answerQuestion(answerDTO);

    assertEquals("Question answered successfully.", response.getBody());
  }

  @Test
  public void answerQuestion_shouldThrowNotFoundException() {
    doThrow(new QuizAssignmentNotFoundException(answerDTO.getQuizAssignmentId())).when(
        quizAssignmentService).answerQuestion(answerDTO);

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentController.answerQuestion(answerDTO).getBody());
  }

  @Test
  public void answerQuestion_shouldThrowQuizNotInProgressException() {
    doThrow(new QuizNotInProgressException(answerDTO.getQuizAssignmentId())).when(
        quizAssignmentService).answerQuestion(answerDTO);

    assertThrows(QuizNotInProgressException.class,
        () -> quizAssignmentController.answerQuestion(answerDTO).getBody());
  }

  @Test
  public void completeQuiz_ShouldReturnOk() {
    Long quizAssignmentId = 1L;
    doNothing().when(quizAssignmentService).completeQuiz(quizAssignmentId);

    ResponseEntity<String> response = quizAssignmentController.completeQuiz(quizAssignmentId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void completeQuiz_ShouldReturnSuccessMessgae() {
    Long quizAssignmentId = 1L;
    doNothing().when(quizAssignmentService).completeQuiz(quizAssignmentId);

    ResponseEntity<String> response = quizAssignmentController.completeQuiz(quizAssignmentId);

    assertEquals("Quiz completed successfully.", response.getBody());
  }

  @Test
  public void completeQuiz_shouldThrowNotFoundException() {
    Long quizAssignmentId = 1L;
    doThrow(new QuizAssignmentNotFoundException(quizAssignmentId)).when(quizAssignmentService)
        .completeQuiz(quizAssignmentId);

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentController.completeQuiz(quizAssignmentId).getBody());
  }
}
