package com.dopingtech.casestudy.quiz_service.service;

import com.dopingtech.casestudy.quiz_service.exception.QuestionNotFoundException;
import com.dopingtech.casestudy.quiz_service.exception.QuizNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.QuestionDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuestionWithoutAnswerDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.repository.OptionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuestionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuizRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private OptionRepository optionRepository;

  public Question findById(Long id) {
    return questionRepository.findById(id)
        .orElseThrow(() -> new QuestionNotFoundException(id));
  }

  public QuestionWithoutAnswerDTO findQuestionWithoutAnswerById(Long id) {
    Question question = questionRepository.findById(id)
        .orElseThrow(() -> new QuestionNotFoundException(id));

    return QuestionWithoutAnswerDTO.builder()
        .id(question.getId())
        .text(question.getText())
        .options(question.getOptions())
        .quizId(question.getQuiz().getId())
        .build();
  }

  public List<Option> findOptionsByQuestionId(Long questionId) {
    if (!questionRepository.existsById(questionId)) {
      throw new QuestionNotFoundException(questionId);
    }
    return optionRepository.findByQuestionId(questionId);
  }

  @CacheEvict(value = "quizQuestions", allEntries = true)
  public Question createQuestion(QuestionDTO questionDTO) {
    Quiz quiz = quizRepository.findById(questionDTO.getQuizId())
        .orElseThrow(() -> new QuizNotFoundException(questionDTO.getQuizId()));

    Question question = new Question();
    question.setText(questionDTO.getText());
    question.setCorrectAnswer(questionDTO.getCorrectAnswer());

    question.setQuiz(quiz);

    return questionRepository.save(question);
  }

  @CacheEvict(value = "quizQuestions", allEntries = true)
  public Question updateQuestion(Long id, QuestionDTO questionDTO) {
    Quiz quiz = quizRepository.findById(questionDTO.getQuizId())
        .orElseThrow(() -> new QuizNotFoundException(questionDTO.getQuizId()));

    return questionRepository.findById(id)
        .map(existingQuestion -> {
          existingQuestion.setText(questionDTO.getText());
          existingQuestion.setCorrectAnswer(questionDTO.getCorrectAnswer());

          existingQuestion.setQuiz(quiz);

          return questionRepository.save(existingQuestion);
        })
        .orElseThrow(() -> new QuestionNotFoundException(id));
  }

  @CacheEvict(value = "quizQuestions", allEntries = true)
  public void deleteQuestion(Long id) {
    if (!questionRepository.existsById(id)) {
      throw new QuestionNotFoundException(id);
    }
    questionRepository.deleteById(id);
  }
}
