package com.dopingtech.casestudy.quiz_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.OptionNotFoundException;
import com.dopingtech.casestudy.quiz_service.exception.QuestionNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.OptionDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.repository.OptionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuestionRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {


  @InjectMocks
  private OptionService optionService;

  @Mock
  private OptionRepository optionRepository;

  @Mock
  private QuestionRepository questionRepository;

  private OptionDTO optionDTO;
  private Option option;
  private Question question;

  @BeforeEach
  public void setUp() {
    optionDTO = new OptionDTO();
    optionDTO.setLetter("A");
    optionDTO.setText("Option Text");
    optionDTO.setQuestionId(1L);
    question = new Question();
    question.setId(1L);
    option = new Option();
    option.setId(1L);
    option.setText(optionDTO.getText());
    option.setLetter(optionDTO.getLetter());
    option.setQuestion(question);
  }

  @Test
  public void findById_shouldReturnOption() {
    when(optionRepository.findById(1L)).thenReturn(Optional.of(option));

    Option foundOption = optionService.findById(1L);

    assertEquals(option.getId(), foundOption.getId());
  }

  @Test
  public void findById_shouldCallFindByIdMethodOnce() {
    when(optionRepository.findById(1L)).thenReturn(Optional.of(option));

    optionService.findById(1L);

    verify(optionRepository, times(1)).findById(1L);
  }

  @Test
  public void findById_shouldThrowOptionNotFoundException() {
    when(optionRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(OptionNotFoundException.class, () -> optionService.findById(1L));
  }

  @Test
  public void createOption_shouldReturnCreatedOption() {
    when(questionRepository.findById(optionDTO.getQuestionId())).thenReturn(Optional.of(question));
    when(optionRepository.save(any(Option.class))).thenReturn(option);

    Option createdOption = optionService.createOption(optionDTO);

    assertEquals(optionDTO.getText(), createdOption.getText());
  }

  @Test
  public void createOption_shouldCallSaveMethodOnce() {
    when(questionRepository.findById(optionDTO.getQuestionId())).thenReturn(Optional.of(question));
    when(optionRepository.save(any(Option.class))).thenReturn(option);

    optionService.createOption(optionDTO);

    verify(optionRepository, times(1)).save(any(Option.class));
  }

  @Test
  public void createOption_shouldThrowQuestionNotFoundException() {
    when(questionRepository.findById(optionDTO.getQuestionId())).thenReturn(Optional.empty());

    assertThrows(QuestionNotFoundException.class, () -> optionService.createOption(optionDTO));
  }

  @Test
  public void updateOption_shouldReturnUpdatedOption() {
    when(optionRepository.findById(1L)).thenReturn(Optional.of(option));
    when(optionRepository.save(any(Option.class))).thenReturn(option);

    Option updatedOption = optionService.updateOption(1L, optionDTO);

    assertEquals(optionDTO.getText(), updatedOption.getText());
  }

  @Test
  public void updateOption_shouldCallSaveMethodOnce() {
    when(optionRepository.findById(1L)).thenReturn(Optional.of(option));
    when(optionRepository.save(any(Option.class))).thenReturn(option);

    optionService.updateOption(1L, optionDTO);

    verify(optionRepository, times(1)).save(any(Option.class));
  }

  @Test
  public void updateOption_shouldThrowOptionNotFoundException() {
    when(optionRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(OptionNotFoundException.class, () -> optionService.updateOption(1L, optionDTO));
  }

  @Test
  public void deleteOption_shouldCallDeleteByIdMethodOnceWithGivenId() {
    when(optionRepository.existsById(1L)).thenReturn(true);

    optionService.deleteOption(1L);

    verify(optionRepository, times(1)).deleteById(1L);
  }

  @Test
  public void deleteOption_shouldThrowOptionNotFoundException() {
    when(optionRepository.existsById(1L)).thenReturn(false);

    assertThrows(OptionNotFoundException.class, () -> optionService.deleteOption(1L));
  }
}