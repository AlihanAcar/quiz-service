package com.dopingtech.casestudy.quiz_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.QuestionNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.QuestionDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuestionWithoutAnswerDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.repository.OptionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuestionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuizRepository;
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
class QuestionServiceTest {

  @InjectMocks
  private QuestionService questionService;

  @Mock
  private QuizRepository quizRepository;

  @Mock
  private QuestionRepository questionRepository;

  @Mock
  private OptionRepository optionRepository;

  private Question question;
  private QuestionDTO questionDTO;
  private Quiz quiz;
  private Option option;

  @BeforeEach
  public void setUp() {
    quiz = new Quiz();
    quiz.setId(1L);
    question = new Question();
    question.setId(1L);
    question.setText("Sample Question");
    question.setCorrectAnswer("A");
    question.setQuiz(quiz);

    questionDTO = new QuestionDTO();
    questionDTO.setQuizId(1L);
    questionDTO.setText("Sample Question");
    questionDTO.setCorrectAnswer("A");
  }

  @Test
  public void findById_shouldReturnQuestion() {
    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

    Question foundQuestion = questionService.findById(1L);

    assertEquals(question.getText(), foundQuestion.getText());
  }

  @Test
  public void findById_shouldCallFindByIdMethodOnce() {
    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

    questionService.findById(1L);

    verify(questionRepository, times(1)).findById(1L);
  }

  @Test
  public void findById_shouldThrowQuestionNotFoundException() {
    when(questionRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(QuestionNotFoundException.class, () -> questionService.findById(1L));
  }

  @Test
  public void getQuestionWithoutAnswer_shouldCallFindByIdMethodOnce() {
    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

    questionService.findQuestionWithoutAnswerById(1L);

    verify(questionRepository, times(1)).findById(1L);
  }

  @Test
  public void getQuestionWithoutAnswer_shouldReturnQuestionWithoutAnswerDTO() {
    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

    QuestionWithoutAnswerDTO result = questionService.findQuestionWithoutAnswerById(1L);

    assertEquals(question.getId(), result.getId());
  }

  @Test
  public void getQuestionWithoutAnswer_shouldThrowQuestionNotFoundException() {
    when(questionRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(QuestionNotFoundException.class,
        () -> questionService.findQuestionWithoutAnswerById(1L));
  }

  @Test
  public void findOptionsByQuestionId_shouldReturnOptions() {
    when(questionRepository.existsById(1L)).thenReturn(true);
    when(optionRepository.findByQuestionId(1L)).thenReturn(Collections.singletonList(option));

    List<Option> options = questionService.findOptionsByQuestionId(1L);

    assertEquals(Collections.singletonList(option), options);
  }

  @Test
  public void findOptionsByQuestionId_shouldCallFindByQuestionIdMethodOnce() {
    when(questionRepository.existsById(1L)).thenReturn(true);
    when(optionRepository.findByQuestionId(1L)).thenReturn(Collections.singletonList(option));

    questionService.findOptionsByQuestionId(1L);

    verify(optionRepository, times(1)).findByQuestionId(1L);
  }

  @Test
  public void findOptionsByQuestionId_shouldThrowQuestionNotFoundException() {
    Long questionId = 1L;
    when(questionRepository.existsById(questionId)).thenReturn(false);

    assertThrows(QuestionNotFoundException.class, () -> {
      questionService.findOptionsByQuestionId(questionId);
    });
  }

  @Test
  public void createQuestion_shouldReturnCreatedQuestion() {
    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    when(questionRepository.save(any(Question.class))).thenReturn(question);

    Question createdQuestion = questionService.createQuestion(questionDTO);

    assertEquals(questionDTO.getText(), createdQuestion.getText());
  }

  @Test
  public void createQuestion_shouldCallSaveMethodOnce() {
    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    when(questionRepository.save(any(Question.class))).thenReturn(question);

    questionService.createQuestion(questionDTO);

    verify(questionRepository, times(1)).save(any(Question.class));
  }

  @Test
  public void updateQuestion_shouldReturnUpdatedQuestion() {
    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
    when(questionRepository.save(any(Question.class))).thenReturn(question);

    Question updatedQuestion = questionService.updateQuestion(1L, questionDTO);

    assertEquals(question.getText(), updatedQuestion.getText());
  }

  @Test
  public void updateQuestion_shouldCallSaveMethodOnce() {
    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
    when(questionRepository.save(any(Question.class))).thenReturn(question);

    questionService.updateQuestion(1L, questionDTO);

    verify(questionRepository, times(1)).save(any(Question.class));
  }

  @Test
  public void updateQuestion_shouldThrowQuestionNotFoundException() {
    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    when(questionRepository.findById(2L)).thenReturn(Optional.empty());

    assertThrows(QuestionNotFoundException.class,
        () -> questionService.updateQuestion(2L, questionDTO));
  }

  @Test
  public void deleteQuestion_shouldCallDeleteByIdMethodOnce() {
    when(questionRepository.existsById(1L)).thenReturn(true);

    questionService.deleteQuestion(1L);

    verify(questionRepository, times(1)).deleteById(1L);
  }

  @Test
  public void deleteQuestion_NonExistingId_ThrowsException() {
    when(questionRepository.existsById(1L)).thenReturn(false);

    assertThrows(QuestionNotFoundException.class, () -> questionService.deleteQuestion(1L));
  }
}