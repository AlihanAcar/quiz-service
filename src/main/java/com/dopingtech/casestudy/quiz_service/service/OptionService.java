package com.dopingtech.casestudy.quiz_service.service;

import com.dopingtech.casestudy.quiz_service.exception.OptionNotFoundException;
import com.dopingtech.casestudy.quiz_service.exception.QuestionNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.OptionDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.repository.OptionRepository;
import com.dopingtech.casestudy.quiz_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

  @Autowired
  private OptionRepository optionRepository;

  @Autowired
  private QuestionRepository questionRepository;

  public Option findById(Long id) {
    return optionRepository.findById(id)
        .orElseThrow(() -> new OptionNotFoundException(id));
  }

  public Option createOption(OptionDTO optionDTO) {
    Question question = questionRepository.findById(optionDTO.getQuestionId())
        .orElseThrow(() -> new QuestionNotFoundException(optionDTO.getQuestionId()));

    Option option = new Option();
    option.setText(optionDTO.getText());
    option.setLetter(optionDTO.getLetter());
    option.setQuestion(question);

    return optionRepository.save(option);
  }

  public Option updateOption(Long id, OptionDTO optionDTO) {
    Option option = optionRepository.findById(id)
        .orElseThrow(() -> new OptionNotFoundException(id));
    option.setText(optionDTO.getText());
    option.setLetter(optionDTO.getLetter());
    return optionRepository.save(option);
  }

  public void deleteOption(Long id) {
    if (!optionRepository.existsById(id)) {
      throw new OptionNotFoundException(id);
    }
    optionRepository.deleteById(id);
  }
}
