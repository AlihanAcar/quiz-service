package com.dopingtech.casestudy.quiz_service.controller;

import com.dopingtech.casestudy.quiz_service.model.dto.QuizDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Question;
import com.dopingtech.casestudy.quiz_service.model.entity.Quiz;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.service.QuizService;
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
@RequestMapping("/quizzes")
@Tag(name = "Quiz API")
@Validated
public class QuizController {

  @Autowired
  private QuizService quizService;

  @GetMapping
  @Operation(summary = "Get all quizzes", description = "Returns a list of all quizzes")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved quizzes")
  })
  public ResponseEntity<List<QuizInfoDTO>> getAllQuizzes() {
    List<QuizInfoDTO> quizzes = quizService.findAll();
    return ResponseEntity.ok(quizzes);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get quiz by id", description = "Returns the quiz corresponding to the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the quiz"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz was not found")
  })
  public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
    Quiz quiz = quizService.findById(id);
    return ResponseEntity.ok(quiz);
  }

  @GetMapping("/{id}/quiz-assignments")
  @Operation(summary = "Get quiz assignments by quiz id", description = "Returns quiz assignments corresponding to the given quiz id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved quiz assignments"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz was not found")
  })
  public ResponseEntity<List<QuizAssignment>> getQuizAssignmentsByQuizId(@PathVariable Long id) {
    List<QuizAssignment> quizAssignments = quizService.findQuizAssignmentsByQuizId(id);
    return ResponseEntity.ok(quizAssignments);
  }

  @GetMapping("/{id}/students")
  @Operation(summary = "Get students by quiz id", description = "Returns students who are assigned to the given quiz id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved students"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz was not found")
  })
  public ResponseEntity<List<StudentInfoDTO>> getStudentsByQuizId(@PathVariable Long id) {
    List<StudentInfoDTO> students = quizService.findStudentsByQuizId(id);
    return ResponseEntity.ok(students);
  }

  @GetMapping("/{id}/questions")
  @Operation(summary = "Get questions by quiz id", description = "Returns questions associated with the given quiz id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved questions"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz was not found")
  })
  public ResponseEntity<List<Question>> getQuestionsByQuizId(@PathVariable Long id) {
    List<Question> questions = quizService.findQuestionsByQuizId(id);
    return ResponseEntity.ok(questions);
  }

  @PostMapping
  @Operation(summary = "Create a new quiz", description = "Creates a new quiz based on the provided details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the quiz"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data")
  })
  public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody QuizDTO quizDTO) {
    Quiz createdQuiz = quizService.createQuiz(quizDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Update a quiz", description = "Updates the existing quiz identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the quiz"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz was not found")
  })
  public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id,
                                         @Valid @RequestBody QuizDTO quizDTO) {
    Quiz updatedQuiz = quizService.updateQuiz(id, quizDTO);
    return ResponseEntity.ok(updatedQuiz);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a quiz", description = "Deletes the quiz identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted the quiz"),
      @ApiResponse(responseCode = "404", description = "Not found - The quiz was not found")
  })
  public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
    quizService.deleteQuiz(id);
    return ResponseEntity.ok("Quiz deleted successfully.");
  }
}
