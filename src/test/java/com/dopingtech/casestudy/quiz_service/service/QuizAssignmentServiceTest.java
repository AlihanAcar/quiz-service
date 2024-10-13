package com.dopingtech.casestudy.quiz_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.QuizAssignmentNotFoundException;
import com.dopingtech.casestudy.quiz_service.exception.QuizCompletedAlreadyException;
import com.dopingtech.casestudy.quiz_service.exception.QuizNotFoundException;
import com.dopingtech.casestudy.quiz_service.exception.QuizNotInProgressException;
import com.dopingtech.casestudy.quiz_service.exception.StudentNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.AnswerDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizAssignmentDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Answer;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.model.enums.QuizAssignmentStatus;
import com.dopingtech.casestudy.quiz_service.repository.AnswerRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuestionRepository;
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
public class QuizAssignmentServiceTest {

  @Mock
  private QuizAssignmentRepository quizAssignmentRepository;

  @Mock
  private AnswerRepository answerRepository;

  @Mock
  private QuestionRepository questionRepository;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private QuizRepository quizRepository;

  @InjectMocks
  private QuizAssignmentService quizAssignmentService;

  private QuizAssignmentDTO quizAssignmentDTO;
  private AnswerDTO answerDTO;

  @BeforeEach
  public void setUp() {
    quizAssignmentDTO = new QuizAssignmentDTO();
    quizAssignmentDTO.setStudentId(1L);
    quizAssignmentDTO.setQuizId(1L);

    answerDTO = new AnswerDTO();
    answerDTO.setQuizAssignmentId(1L);
    answerDTO.setQuestionId(1L);
    answerDTO.setSelectedOption("A");
  }


  @Test
  public void findAll_shouldReturnAllQuizAssignments() {
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentRepository.findAll()).thenReturn(Collections.singletonList(assignment));

    List<QuizAssignment> assignments = quizAssignmentService.findAll();

    assertEquals(Collections.singletonList(assignment), assignments);
  }

  @Test
  public void findAll_shouldCallFindAllMethodOnce() {
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentRepository.findAll()).thenReturn(Collections.singletonList(assignment));

    quizAssignmentService.findAll();

    verify(quizAssignmentRepository, times(1)).findAll();
  }

  @Test
  public void testFindAnswersByQuizAssignmentId_ShouldReturnAnswers() {
    Long quizAssignmentId = 1L;
    Answer answer = new Answer();
    when(quizAssignmentRepository.existsById(quizAssignmentId)).thenReturn(true);
    when(answerRepository.findByQuizAssignmentId(quizAssignmentId)).thenReturn(
        Collections.singletonList(answer));

    List<Answer> answers = quizAssignmentService.findAnswersByQuizAssignmentId(quizAssignmentId);

    assertEquals(Collections.singletonList(answer), answers);
  }

  @Test
  public void findAnswersByQuizAssignmentId_shouldCallFindByQuizAssignmentIdMethodOnce() {
    Long quizAssignmentId = 1L;
    Answer answer = new Answer();
    when(answerRepository.findByQuizAssignmentId(quizAssignmentId)).thenReturn(
        Collections.singletonList(answer));

    answerRepository.findByQuizAssignmentId(quizAssignmentId);

    verify(answerRepository, times(1)).findByQuizAssignmentId(1L);
  }

  @Test
  public void findAnswersByQuizAssignmentId_shouldThrowQuizAssignmentNotFoundException() {
    Long quizAssignmentId = 1L;

    when(quizAssignmentRepository.existsById(quizAssignmentId)).thenReturn(false);

    assertThrows(QuizAssignmentNotFoundException.class, () -> {
      quizAssignmentService.findAnswersByQuizAssignmentId(quizAssignmentId);
    });
  }

  @Test
  public void findById_shouldReturnQuizAssignment() {
    Long id = 1L;
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentRepository.findById(id)).thenReturn(Optional.of(assignment));

    QuizAssignment result = quizAssignmentService.findById(id);

    assertEquals(assignment, result);
  }

  @Test
  public void findById_shouldCallFindByIdMethodOnce() {
    Long id = 1L;
    QuizAssignment assignment = new QuizAssignment();
    when(quizAssignmentRepository.findById(id)).thenReturn(Optional.of(assignment));

    quizAssignmentService.findById(id);

    verify(quizAssignmentRepository, times(1)).findById(id);
  }

  @Test
  public void findById_shouldThrowQuizAssignmentNotFoundException() {
    Long id = 1L;
    when(quizAssignmentRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(QuizAssignmentNotFoundException.class, () -> quizAssignmentService.findById(id));
  }

  @Test
  public void createQuizAssignment_shouldReturnCreatedAssignment() {
    QuizAssignment quizAssignment = new QuizAssignment();
    quizAssignment.setId(1L);

    when(studentRepository.existsById(1L)).thenReturn(true);
    when(quizRepository.existsById(1L)).thenReturn(true);
    when(quizAssignmentRepository.save(any(QuizAssignment.class))).thenReturn(quizAssignment);

    QuizAssignment createdAssignment =
        quizAssignmentService.createQuizAssignment(quizAssignmentDTO);

    assertNotNull(createdAssignment);
  }

  @Test
  public void createQuizAssignment_shouldCallSavedMethodOnce() {
    when(studentRepository.existsById(1L)).thenReturn(true);
    when(quizRepository.existsById(1L)).thenReturn(true);

    quizAssignmentService.createQuizAssignment(quizAssignmentDTO);

    verify(quizAssignmentRepository, times(1)).save(any(QuizAssignment.class));
  }

  @Test
  public void createQuizAssignment_shouldThrowStudentNotFoundException() {
    doReturn(false).when(studentRepository).existsById(1L);

    assertThrows(StudentNotFoundException.class, () -> {
      quizAssignmentService.createQuizAssignment(quizAssignmentDTO);
    });
  }

  @Test
  public void createQuizAssignment_shouldThrowQuizNotFoundException() {
    doReturn(true).when(studentRepository).existsById(1L);
    doReturn(false).when(quizRepository).existsById(1L);

    assertThrows(QuizNotFoundException.class, () -> {
      quizAssignmentService.createQuizAssignment(quizAssignmentDTO);
    });
  }

  @Test
  public void startQuiz_ShouldUpdateStatusToInProgress() {
    Long quizAssignmentId = 1L;
    QuizAssignment assignment = new QuizAssignment();
    assignment.setStatus(QuizAssignmentStatus.ASSIGNED);

    when(quizAssignmentRepository.findById(quizAssignmentId)).thenReturn(Optional.of(assignment));

    quizAssignmentService.startQuiz(quizAssignmentId);

    assertEquals(QuizAssignmentStatus.IN_PROGRESS, assignment.getStatus());
  }

  @Test
  public void startQuiz_shouldCallSavedMethodOnce() {
    Long quizAssignmentId = 1L;
    QuizAssignment assignment = new QuizAssignment();
    assignment.setStatus(QuizAssignmentStatus.ASSIGNED);

    when(quizAssignmentRepository.findById(quizAssignmentId)).thenReturn(Optional.of(assignment));

    quizAssignmentService.startQuiz(quizAssignmentId);

    verify(quizAssignmentRepository, times(1)).save(assignment);
  }

  @Test
  public void startQuiz_shouldThrowQuizAssignmentNotFoundException() {
    Long quizAssignmentId = 1L;
    when(quizAssignmentRepository.findById(quizAssignmentId)).thenReturn(Optional.empty());

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentService.startQuiz(quizAssignmentId));
  }


  @Test
  public void startQuiz_ShouldThrowQuizCompletedAlreadyException() {
    Long quizAssignmentId = 1L;
    QuizAssignment completedAssignment = new QuizAssignment();
    completedAssignment.setStatus(QuizAssignmentStatus.COMPLETED);

    when(quizAssignmentRepository.findById(quizAssignmentId)).thenReturn(Optional.of(completedAssignment));

    assertThrows(QuizCompletedAlreadyException.class, () -> {
      quizAssignmentService.startQuiz(quizAssignmentId);
    });
  }

  @Test
  public void answerQuestion_shouldCallSavedMethodOnce() {
    Question question = new Question();
    question.setCorrectAnswer("A");

    QuizAssignment quizAssignment = new QuizAssignment();
    quizAssignment.setStatus(QuizAssignmentStatus.IN_PROGRESS);

    when(quizAssignmentRepository.findById(answerDTO.getQuizAssignmentId())).thenReturn(
        Optional.of(quizAssignment));
    when(answerRepository.findByQuizAssignmentIdAndQuestionId(1L, 1L)).thenReturn(null);
    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

    quizAssignmentService.answerQuestion(answerDTO);

    verify(answerRepository, times(1)).save(any(Answer.class));
  }

  @Test
  public void answerQuestion_shouldThrowQuizAssignmentNotFoundException() {
    when(quizAssignmentRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentService.answerQuestion(answerDTO));
  }

  @Test
  public void answerQuestion_shouldThrowQuizNotInProgressException() {
    QuizAssignment quizAssignment = new QuizAssignment();
    quizAssignment.setStatus(QuizAssignmentStatus.COMPLETED);

    when(quizAssignmentRepository.findById(1L)).thenReturn(Optional.of(quizAssignment));

    assertThrows(QuizNotInProgressException.class,
        () -> quizAssignmentService.answerQuestion(answerDTO));
  }

  @Test
  public void completeQuiz_ShouldUpdateStatusToCompleted() {
    Quiz quiz = new Quiz();

    Question question = new Question();

    List<Question> questions = new ArrayList<>();
    questions.add(question);

    quiz.setQuestions(questions);

    Long quizAssignmentId = 1L;
    QuizAssignment assignment = new QuizAssignment();
    assignment.setStatus(QuizAssignmentStatus.IN_PROGRESS);
    assignment.setAnswers(new ArrayList<>());
    assignment.setQuiz(quiz);

    when(quizAssignmentRepository.findById(quizAssignmentId)).thenReturn(Optional.of(assignment));

    quizAssignmentService.completeQuiz(quizAssignmentId);

    assertEquals(QuizAssignmentStatus.COMPLETED, assignment.getStatus());
  }

  @Test
  public void completeQuiz_shouldCallSavedMethodOnce() {
    Quiz quiz = new Quiz();

    Question question = new Question();

    List<Question> questions = new ArrayList<>();
    questions.add(question);

    quiz.setQuestions(questions);

    Long quizAssignmentId = 1L;
    QuizAssignment assignment = new QuizAssignment();
    assignment.setStatus(QuizAssignmentStatus.IN_PROGRESS);
    assignment.setAnswers(new ArrayList<>());
    assignment.setQuiz(quiz);

    when(quizAssignmentRepository.findById(quizAssignmentId)).thenReturn(Optional.of(assignment));

    quizAssignmentService.completeQuiz(quizAssignmentId);

    assertEquals(QuizAssignmentStatus.COMPLETED, assignment.getStatus());
    verify(quizAssignmentRepository, times(1)).save(assignment);
  }

  @Test
  public void completeQuiz_shouldThrowQuizAssignmentNotFoundException() {
    Long quizAssignmentId = 1L;
    when(quizAssignmentRepository.findById(quizAssignmentId)).thenReturn(Optional.empty());

    assertThrows(QuizAssignmentNotFoundException.class,
        () -> quizAssignmentService.completeQuiz(quizAssignmentId));
  }
}