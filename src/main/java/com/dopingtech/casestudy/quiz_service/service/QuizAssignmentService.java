package com.dopingtech.casestudy.quiz_service.service;

import com.dopingtech.casestudy.quiz_service.exception.QuestionNotFoundException;
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
import com.dopingtech.casestudy.quiz_service.model.entity.Student;
import com.dopingtech.casestudy.quiz_service.model.enums.QuizAssignmentStatus;
import com.dopingtech.casestudy.quiz_service.repository.AnswerRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuestionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuizAssignmentRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuizRepository;
import com.dopingtech.casestudy.quiz_service.repository.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class QuizAssignmentService {

  @Autowired
  private QuizAssignmentRepository quizAssignmentRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private QuizRepository quizRepository;

  @Cacheable(value = "quizAssignments")
  public List<QuizAssignment> findAll() {
    return quizAssignmentRepository.findAll();
  }

  public List<Answer> findAnswersByQuizAssignmentId(Long quizAssignmentId) {
    if (!quizAssignmentRepository.existsById(quizAssignmentId)) {
      throw new QuizAssignmentNotFoundException(quizAssignmentId);
    }
    return answerRepository.findByQuizAssignmentId(quizAssignmentId);
  }

  public QuizAssignment findById(Long id) {
    return quizAssignmentRepository.findById(id)
        .orElseThrow(() -> new QuizAssignmentNotFoundException(id));
  }

  @CacheEvict(value = "quizAssignments", allEntries = true)
  public QuizAssignment createQuizAssignment(QuizAssignmentDTO quizAssignmentDTO) {
    QuizAssignment quizAssignment = new QuizAssignment();

    if (!studentRepository.existsById(quizAssignmentDTO.getStudentId())) {
      throw new StudentNotFoundException(quizAssignmentDTO.getStudentId());
    }

    if (!quizRepository.existsById(quizAssignmentDTO.getQuizId())) {
      throw new QuizNotFoundException(quizAssignmentDTO.getQuizId());
    }

    Student student = new Student();
    student.setId(quizAssignmentDTO.getStudentId());
    quizAssignment.setStudent(student);

    Quiz quiz = new Quiz();
    quiz.setId(quizAssignmentDTO.getQuizId());
    quizAssignment.setQuiz(quiz);

    quizAssignment.setStatus(QuizAssignmentStatus.ASSIGNED);
    quizAssignment.setScore(0);
    return quizAssignmentRepository.save(quizAssignment);
  }

  @CacheEvict(value = "quizAssignments", allEntries = true)
  public void startQuiz(Long quizAssignmentId) {
    QuizAssignment quizAssignment = quizAssignmentRepository.findById(quizAssignmentId)
        .orElseThrow(() -> new QuizAssignmentNotFoundException(quizAssignmentId));

    if (quizAssignment.getStatus() == QuizAssignmentStatus.COMPLETED) {
      throw new QuizCompletedAlreadyException(quizAssignmentId);
    }

    quizAssignment.setStatus(QuizAssignmentStatus.IN_PROGRESS);
    quizAssignmentRepository.save(quizAssignment);
  }

  @CacheEvict(value = "quizAssignments", allEntries = true)
  public void answerQuestion(AnswerDTO answerDTO) {
    QuizAssignment quizAssignment =
        quizAssignmentRepository.findById(answerDTO.getQuizAssignmentId())
            .orElseThrow(
                () -> new QuizAssignmentNotFoundException(answerDTO.getQuizAssignmentId()));

    if (quizAssignment.getStatus() != QuizAssignmentStatus.IN_PROGRESS) {
      throw new QuizNotInProgressException(answerDTO.getQuizAssignmentId());
    }

    Answer answer = answerRepository.findByQuizAssignmentIdAndQuestionId(
        answerDTO.getQuizAssignmentId(), answerDTO.getQuestionId());

    if (answer == null) {
      answer = new Answer();
      answer.setQuizAssignment(quizAssignment);
    }

    Question question = new Question();
    question.setId(answerDTO.getQuestionId());

    answer.setSelectedOption(answerDTO.getSelectedOption());
    answer.setCorrect(checkAnswer(answerDTO.getQuestionId(), answerDTO.getSelectedOption()));
    answer.setQuestion(question);

    answerRepository.save(answer);
  }

  @CacheEvict(value = "quizAssignments", allEntries = true)
  public void completeQuiz(Long quizAssignmentId) {
    QuizAssignment quizAssignment = quizAssignmentRepository.findById(quizAssignmentId)
        .orElseThrow(() -> new QuizAssignmentNotFoundException(quizAssignmentId));
    quizAssignment.setStatus(QuizAssignmentStatus.COMPLETED);
    evaluateQuizScore(quizAssignment);
    quizAssignmentRepository.save(quizAssignment);
  }

  private void evaluateQuizScore(QuizAssignment quizAssignment) {
    int correctAnswers =
        (int) quizAssignment.getAnswers().stream().filter(Answer::isCorrect).count();

    quizAssignment.setCorrectAnswerCount(correctAnswers);
    quizAssignment.setScore(
        correctAnswers / quizAssignment.getQuiz().getQuestions().size() * 100);
  }

  private boolean checkAnswer(Long questionId, String selectedOption) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new QuestionNotFoundException(questionId));

    return question.getCorrectAnswer().equals(selectedOption);
  }
}
