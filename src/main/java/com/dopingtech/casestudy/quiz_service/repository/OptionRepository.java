package com.dopingtech.casestudy.quiz_service.repository;

import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {

  List<Option> findByQuestionId(Long questionId);
}
