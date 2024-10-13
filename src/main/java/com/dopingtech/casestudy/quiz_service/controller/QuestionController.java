package com.dopingtech.casestudy.quiz_service.controller;

import com.dopingtech.casestudy.quiz_service.model.dto.QuestionDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuestionWithoutAnswerDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@Tag(name = "Question API")
@Validated
public class QuestionController {

  @Autowired
  private QuestionService questionService;

  @GetMapping("/{id}")
  @Operation(summary = "Get a question by id", description = "Returns the question corresponding to the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the question"),
      @ApiResponse(responseCode = "404", description = "Not found - The question was not found")
  })
  public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
    Question question = questionService.findById(id);
    return ResponseEntity.ok(question);
  }

  @GetMapping("/{id}/without-answer")
  @Operation(summary = "Get a question without answer by id", description = "Returns the question without the correct answer corresponding to the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the question without answer"),
      @ApiResponse(responseCode = "404", description = "Not found - The question was not found")
  })
  public ResponseEntity<QuestionWithoutAnswerDTO> getQuestionWithoutAnswerById(@PathVariable Long id) {
    QuestionWithoutAnswerDTO questionDTO = questionService.findQuestionWithoutAnswerById(id);
    return ResponseEntity.ok(questionDTO);
  }

  @GetMapping("/{questionId}/options")
  @Operation(summary = "Get options by question id", description = "Returns options corresponding to the given question id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved options"),
      @ApiResponse(responseCode = "404", description = "Not found - The question was not found")
  })
  public ResponseEntity<List<Option>> getOptionsByQuestionId(@PathVariable Long questionId) {
    List<Option> options = questionService.findOptionsByQuestionId(questionId);
    return ResponseEntity.ok(options);
  }

  @PostMapping
  @Operation(summary = "Create a new question", description = "Creates a new question with the provided details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the question"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz was not found provided id")
  })
  public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
    Question createdQuestion = questionService.createQuestion(questionDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Update a question", description = "Updates the existing question identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the question"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Not found - The question was not found")
  })
  public ResponseEntity<Question> updateQuestion(@PathVariable Long id,
                                                 @Valid @RequestBody QuestionDTO questionDTO) {
    Question updatedQuestion = questionService.updateQuestion(id, questionDTO);
    return ResponseEntity.ok(updatedQuestion);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a question", description = "Deletes the question identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted the question"),
      @ApiResponse(responseCode = "404", description = "Not found - The question was not found")
  })
  public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
    questionService.deleteQuestion(id);
    return ResponseEntity.ok("Question deleted successfully.");
  }
}