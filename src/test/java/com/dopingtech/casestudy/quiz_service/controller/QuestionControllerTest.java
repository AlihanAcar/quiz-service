package com.dopingtech.casestudy.quiz_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.QuestionNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.QuestionDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuestionWithoutAnswerDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.service.QuestionService;
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
public class QuestionControllerTest {

  @InjectMocks
  private QuestionController questionController;

  @Mock
  private QuestionService questionService;

  private Question question;
  private QuestionDTO questionDTO;
  private QuestionWithoutAnswerDTO questionWithoutAnswerDTO;

  @BeforeEach
  public void setUp() {
    question = new Question();
    question.setId(1L);
    question.setText("Sample Question");
    question.setCorrectAnswer("A");

    questionDTO = new QuestionDTO();
    questionDTO.setText("Sample Question");
    questionDTO.setCorrectAnswer("A");
  }

  @Test
  public void getQuestionById_shouldReturnOK() {
    when(questionService.findById(1L)).thenReturn(question);

    ResponseEntity<Question> response = questionController.getQuestionById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuestionById_shouldReturnQuestion() {
    when(questionService.findById(1L)).thenReturn(question);

    ResponseEntity<Question> response = questionController.getQuestionById(1L);

    assertEquals(question, response.getBody());
  }

  @Test
  public void getQuestionById_shouldThrowQuestionNotFoundException() {
    when(questionService.findById(1L)).thenThrow(new QuestionNotFoundException(1L));

    assertThrows(QuestionNotFoundException.class, () -> questionController.getQuestionById(1L));
  }

  @Test
  public void getQuestionWithoutAnswer_shouldReturnOK() {
    when(questionService.findQuestionWithoutAnswerById(1L)).thenReturn(questionWithoutAnswerDTO);

    ResponseEntity<QuestionWithoutAnswerDTO> response =
        questionController.getQuestionWithoutAnswerById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getQuestionWithoutAnswer_shouldReturnQuestionWithoutAnswerDTO() {
    when(questionService.findQuestionWithoutAnswerById(1L)).thenReturn(questionWithoutAnswerDTO);

    ResponseEntity<QuestionWithoutAnswerDTO> response =
        questionController.getQuestionWithoutAnswerById(1L);

    assertEquals(questionWithoutAnswerDTO, response.getBody());
  }

  @Test
  public void getQuestionWithoutAnswer_shouldThrowQuestionNotFoundException() {
    when(questionService.findQuestionWithoutAnswerById(1L)).thenThrow(new QuestionNotFoundException(1L));

    assertThrows(QuestionNotFoundException.class,
        () -> questionController.getQuestionWithoutAnswerById(1L));
  }

  @Test
  public void getOptionsByQuestionId_shouldReturnOK() {
    List<Option> options = List.of(new Option(), new Option());
    when(questionService.findOptionsByQuestionId(1L)).thenReturn(options);

    ResponseEntity<List<Option>> response = questionController.getOptionsByQuestionId(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getOptionsByQuestionId_shouldReturnOptions() {
    List<Option> options = List.of(new Option(), new Option());
    when(questionService.findOptionsByQuestionId(1L)).thenReturn(options);

    ResponseEntity<List<Option>> response = questionController.getOptionsByQuestionId(1L);

    assertEquals(options, response.getBody());
  }

  @Test
  public void createQuestion_shouldReturnCreated() {
    when(questionService.createQuestion(any(QuestionDTO.class))).thenReturn(question);

    ResponseEntity<Question> response = questionController.createQuestion(questionDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void createQuestion_shouldReturnCreatedQuestion() {
    when(questionService.createQuestion(any(QuestionDTO.class))).thenReturn(question);

    ResponseEntity<Question> response = questionController.createQuestion(questionDTO);

    assertEquals(question, response.getBody());
  }

  @Test
  public void createQuestion_shouldThrowQuestionNotFoundException() {
    when(questionService.createQuestion(any(QuestionDTO.class))).thenThrow(
        new QuestionNotFoundException(1L));

    assertThrows(QuestionNotFoundException.class,
        () -> questionController.createQuestion(questionDTO));
  }

  @Test
  public void updateQuestion_shouldReturnOK() {
    when(questionService.updateQuestion(1L, questionDTO)).thenReturn(question);

    ResponseEntity<Question> response = questionController.updateQuestion(1L, questionDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void updateQuestion_shouldReturnUpdatedQuestion() {
    when(questionService.updateQuestion(1L, questionDTO)).thenReturn(question);

    ResponseEntity<Question> response = questionController.updateQuestion(1L, questionDTO);

    assertEquals(question, response.getBody());
  }

  @Test
  public void updateQuestion_shouldThrowQuestionNotFoundException() {
    when(questionService.updateQuestion(1L, questionDTO)).thenThrow(
        new QuestionNotFoundException(1L));

    assertThrows(QuestionNotFoundException.class,
        () -> questionController.updateQuestion(1L, questionDTO));
  }

  @Test
  public void deleteQuestion_shouldReturnOK() {
    doNothing().when(questionService).deleteQuestion(1L);

    ResponseEntity<String> response = questionController.deleteQuestion(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void deleteQuestion_shouldReturnSuccessMessage() {
    doNothing().when(questionService).deleteQuestion(1L);

    ResponseEntity<String> response = questionController.deleteQuestion(1L);

    assertEquals("Question deleted successfully.", response.getBody());
  }

  @Test
  public void deleteQuestion_shouldThrowQuestionNotFoundException() {
    doThrow(new QuestionNotFoundException(1L)).when(questionService).deleteQuestion(1L);

    assertThrows(QuestionNotFoundException.class, () -> questionController.deleteQuestion(1L));
  }
}