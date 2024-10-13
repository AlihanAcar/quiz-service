package com.dopingtech.casestudy.quiz_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class QuizServiceTest {

  @Mock
  private QuizRepository quizRepository;

  @Mock
  private QuizAssignmentRepository quizAssignmentRepository;

  @Mock
  private QuestionRepository questionRepository;

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private QuizService quizService;

  private Quiz quiz;
  private QuizAssignment quizAssignment;
  private Question question;

  @BeforeEach
  public void setUp() {
    quiz = new Quiz();
    quiz.setId(1L);
    quiz.setName("Sample Quiz");

    quizAssignment = new QuizAssignment();
  }

  @Test
  public void findAll_shouldReturnAllQuizzes() {
    List<Quiz> quizzes = Collections.singletonList(quiz);
    when(quizRepository.findAll()).thenReturn(quizzes);

    List<QuizInfoDTO> result = quizService.findAll();

    assertEquals(quizzes.get(0).getId(), result.get(0).getId());
  }

  @Test
  public void findAll_shouldCallFindAllMethodOnce() {
    when(quizRepository.findAll()).thenReturn(Collections.singletonList(quiz));

    quizService.findAll();

    verify(quizRepository, times(1)).findAll();
  }

  @Test
  public void findById_shouldReturnQuiz() {
    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

    Quiz foundQuiz = quizService.findById(1L);

    assertEquals(quiz.getName(), foundQuiz.getName());
  }

  @Test
  public void findById_shouldCallFindByIdMethodOnce() {
    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

    quizService.findById(1L);

    verify(quizRepository, times(1)).findById(1L);
  }

  @Test
  public void findById_shouldThrowQuizNotFoundException() {
    when(quizRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(QuizNotFoundException.class, () -> quizService.findById(1L));
  }

  @Test
  public void findQuizAssignmentsByQuizId_shouldReturnAssignments() {
    when(quizAssignmentRepository.findByQuizId(1L)).thenReturn(
        Collections.singletonList(quizAssignment));
    when(quizRepository.existsById(1L)).thenReturn(true);

    List<QuizAssignment> assignments = quizService.findQuizAssignmentsByQuizId(1L);

    assertEquals(Collections.singletonList(quizAssignment), assignments);
    verify(quizAssignmentRepository).findByQuizId(1L);
  }

  @Test
  public void findQuizAssignmentsByQuizId_shouldCallFindByQuizIdMethodOnce() {
    when(quizAssignmentRepository.findByQuizId(1L)).thenReturn(
        Collections.singletonList(quizAssignment));
    when(quizRepository.existsById(1L)).thenReturn(true);

    quizService.findQuizAssignmentsByQuizId(1L);

    verify(quizAssignmentRepository, times(1)).findByQuizId(1L);
  }

  @Test
  public void findQuizAssignmentsByQuizId_shouldThrowQuizNotFoundException() {
    Long quizId = 1L;
    when(quizRepository.existsById(quizId)).thenReturn(false);

    assertThrows(QuizNotFoundException.class,
        () -> quizService.findQuizAssignmentsByQuizId(quizId));
  }

  @Test
  public void findStudentsByQuizId_shouldReturnStudents() {
    Student student = new Student();
    student.setId(1L);
    student.setFirstName("First Name");
    student.setLastName("Last Name");
    student.setNumber("1234");

    when(quizRepository.existsById(student.getId())).thenReturn(true);
    when(studentRepository.findByQuizAssignmentsQuizId(1L)).thenReturn(List.of(student));

    List<StudentInfoDTO> students = quizService.findStudentsByQuizId(1L);

    assertEquals(student.getId(), students.get(0).getId());
  }

  @Test
  public void findStudentsByQuizId_shouldCallFindByQuizIdMethodOnce() {
    Student student = new Student();
    student.setId(1L);
    student.setFirstName("First Name");
    student.setLastName("Last Name");
    student.setNumber("1234");

    when(quizRepository.existsById(student.getId())).thenReturn(true);
    when(studentRepository.findByQuizAssignmentsQuizId(1L)).thenReturn(List.of(student));

    quizService.findStudentsByQuizId(1L);

    verify(studentRepository, times(1)).findByQuizAssignmentsQuizId(1L);
  }

  @Test
  public void findStudentsByQuizId_shouldThrowQuizNotFoundException() {
    Long quizId = 1L;
    when(quizRepository.existsById(quizId)).thenReturn(false);

    assertThrows(QuizNotFoundException.class, () -> quizService.findStudentsByQuizId(quizId));
  }

  @Test
  public void findQuestionsByQuizId_shouldReturnQuestions() {
    when(questionRepository.findByQuizId(1L)).thenReturn(Collections.singletonList(question));
    when(quizRepository.existsById(1L)).thenReturn(true);

    List<Question> questions = quizService.findQuestionsByQuizId(1L);

    assertEquals(Collections.singletonList(question), questions);
  }

  @Test
  public void findQuestionsByQuizId_shouldCallFindByQuizIdMethodOnce() {
    when(questionRepository.findByQuizId(1L)).thenReturn(Collections.singletonList(question));
    when(quizRepository.existsById(1L)).thenReturn(true);

    quizService.findQuestionsByQuizId(1L);

    verify(questionRepository, times(1)).findByQuizId(1L);
  }

  @Test
  public void findQuestionsByQuizId_shouldThrowQuizNotFoundException() {
    Long quizId = 1L;
    when(quizRepository.existsById(quizId)).thenReturn(false);

    assertThrows(QuizNotFoundException.class, () -> quizService.findQuestionsByQuizId(quizId));
  }

  @Test
  public void createQuiz_shouldReturnCreatedQuiz() {
    QuizDTO quizDTO = new QuizDTO();
    quizDTO.setName("Sample Quiz");

    when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

    Quiz createdQuiz = quizService.createQuiz(quizDTO);

    assertEquals(quiz.getName(), createdQuiz.getName());
  }

  @Test
  public void createQuiz_shouldCallSaveMethodOnce() {
    QuizDTO quizDTO = new QuizDTO();
    quizDTO.setName("Sample Quiz");

    when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

    quizService.createQuiz(quizDTO);

    verify(quizRepository, times(1)).save(any(Quiz.class));
  }

  @Test
  public void updateQuiz_shouldReturnUpdatedQuiz() {
    QuizDTO quizDTO = new QuizDTO();
    quizDTO.setName("Updated Quiz");

    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

    Quiz updatedQuiz = quizService.updateQuiz(1L, quizDTO);

    assertEquals("Updated Quiz", updatedQuiz.getName());
  }

  @Test
  public void updateQuiz_shouldCallSaveMethodOnce() {
    QuizDTO quizDTO = new QuizDTO();
    quizDTO.setName("Updated Quiz");

    when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
    when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

    quizService.updateQuiz(1L, quizDTO);

    verify(quizRepository, times(1)).save(any(Quiz.class));
  }

  @Test
  public void updateQuiz_shouldThrowQuizNotFoundException() {
    QuizDTO quizDTO = new QuizDTO();
    quizDTO.setName("Updated Quiz");

    when(quizRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(QuizNotFoundException.class,
        () -> quizService.updateQuiz(1L, quizDTO));
  }

  @Test
  public void deleteQuiz_shouldCallDeleteByIdMethodOnce() {
    when(quizRepository.existsById(1L)).thenReturn(true);

    quizService.deleteQuiz(1L);

    verify(quizRepository, times(1)).deleteById(1L);
  }

  @Test
  public void deleteQuiz_shouldThrowQuizNotFoundException() {
    when(quizRepository.existsById(1L)).thenReturn(false);

    assertThrows(QuizNotFoundException.class, () -> quizService.deleteQuiz(1L));
  }
}
