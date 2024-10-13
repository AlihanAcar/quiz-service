package com.dopingtech.casestudy.quiz_service.controller;

import com.dopingtech.casestudy.quiz_service.model.dto.AnswerDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizAssignmentDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Answer;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.service.QuizAssignmentService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz-assignments")
@Tag(name = "Quiz Assignment API")
@Validated
public class QuizAssignmentController {

  @Autowired
  private QuizAssignmentService quizAssignmentService;

  @GetMapping
  @Operation(summary = "Get all quiz assignments", description = "Returns a list of all quiz assignments")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved quiz assignments")
  })
  public ResponseEntity<List<QuizAssignment>> getAllQuizAssignments() {
    List<QuizAssignment> quizAssignments = quizAssignmentService.findAll();
    return ResponseEntity.ok(quizAssignments);
  }

  @GetMapping("/{quizAssignmentId}/answers")
  @Operation(summary = "Get answers by quiz assignment id", description = "Returns answers corresponding to the given quiz assignment id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved answers"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz assignment was not found")
  })
  public ResponseEntity<List<Answer>> getAnswersByQuizAssignmentId(
      @PathVariable Long quizAssignmentId) {
    List<Answer> answers = quizAssignmentService.findAnswersByQuizAssignmentId(quizAssignmentId);
    return ResponseEntity.ok(answers);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get quiz assignment by id", description = "Returns the quiz assignment corresponding to the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the quiz assignment"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz assignment was not found")
  })
  public ResponseEntity<QuizAssignment> getQuizAssignmentById(@PathVariable Long id) {
    QuizAssignment quizAssignment = quizAssignmentService.findById(id);
    return ResponseEntity.ok(quizAssignment);
  }

  @PostMapping
  @Operation(summary = "Create a new quiz assignment", description = "Creates a new quiz assignment based on the provided details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the quiz assignment"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Not found - The specified student or quiz was not found")
  })
  public ResponseEntity<QuizAssignment> createQuizAssignment(
      @Valid @RequestBody QuizAssignmentDTO quizAssignmentDTO) {
    QuizAssignment quizAssignment = quizAssignmentService.createQuizAssignment(quizAssignmentDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(quizAssignment);
  }

  @PatchMapping("/{quizAssignmentId}/start")
  @Operation(summary = "Start the quiz", description = "Updates the status of the quiz assignment to in progress")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully started the quiz"),
      @ApiResponse(responseCode = "400", description = "Bad request - Quiz has already been completed"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz assignment was not found")
  })
  public ResponseEntity<String> startQuiz(@PathVariable Long quizAssignmentId) {
    quizAssignmentService.startQuiz(quizAssignmentId);
    return ResponseEntity.ok("Quiz started successfully.");
  }

  @PatchMapping("/answer")
  @Operation(summary = "Submit an answer for a quiz question", description = "Submits the answer for a specific quiz question")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully submitted the answer"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data or quiz is not in progress"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz assignment was not found")

  })
  public ResponseEntity<String> answerQuestion(@Valid @RequestBody AnswerDTO answerDTO) {
    quizAssignmentService.answerQuestion(answerDTO);
    return ResponseEntity.ok("Question answered successfully.");
  }

  @PatchMapping("/{quizAssignmentId}/complete")
  @Operation(summary = "Complete the quiz", description = "Updates the status of the quiz assignment to completed")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully completed the quiz"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz assignment was not found")
  })
  public ResponseEntity<String> completeQuiz(@PathVariable Long quizAssignmentId) {
    quizAssignmentService.completeQuiz(quizAssignmentId);
    return ResponseEntity.ok("Quiz completed successfully.");
  }
}