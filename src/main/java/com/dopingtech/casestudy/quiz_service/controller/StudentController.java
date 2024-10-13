package com.dopingtech.casestudy.quiz_service.controller;

import com.dopingtech.casestudy.quiz_service.model.dto.CompletedQuizDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.QuizInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentDTO;
import com.dopingtech.casestudy.quiz_service.model.dto.StudentInfoDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.QuizAssignment;
import com.dopingtech.casestudy.quiz_service.model.entity.Student;
import com.dopingtech.casestudy.quiz_service.service.StudentService;
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
@RequestMapping("/students")
@Tag(name = "Student API")
@Validated
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping
  @Operation(summary = "Get all students", description = "Returns a list of all students without quiz assignments")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved students")
  })
  public ResponseEntity<List<StudentInfoDTO>> getAllStudents() {
    List<StudentInfoDTO> students = studentService.findAll();
    return ResponseEntity.ok(students);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get student by id", description = "Returns the student corresponding to the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the student"),
      @ApiResponse(responseCode = "404", description = "Not found - The student was not found")
  })
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    Student student = studentService.findById(id);
    return ResponseEntity.ok(student);
  }

  @GetMapping("/{id}/quiz-assignments")
  @Operation(summary = "Get quiz assignments by student id", description = "Returns quiz assignments corresponding to the given student id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved quiz assignments"),
      @ApiResponse(responseCode = "404", description = "Not found - The student was not found")
  })
  public ResponseEntity<List<QuizAssignment>> getQuizAssignmentsByStudentId(@PathVariable Long id) {
    List<QuizAssignment> quizAssignments = studentService.findQuizAssignmentsByStudentId(id);
    return ResponseEntity.ok(quizAssignments);
  }

  @GetMapping("/{studentId}/quizzes")
  @Operation(summary = "Get quizzes by student id", description = "Returns quizzes associated with the given student id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved quizzes"),
      @ApiResponse(responseCode = "404", description = "Not found - The student was not found")
  })
  public ResponseEntity<List<QuizInfoDTO>> getQuizzesByStudentId(@PathVariable Long studentId) {
    List<QuizInfoDTO> quizzes = studentService.findQuizzesByStudentId(studentId);
    return ResponseEntity.ok(quizzes);
  }

  @GetMapping("/{studentId}/completed-quizzes")
  @Operation(
      summary = "Get completed quizzes for a student",
      description = "Returns a list of completed quizzes and their scores for the specified student."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved completed quizzes"),
      @ApiResponse(responseCode = "404", description = "Student not found")
  })
  public ResponseEntity<List<CompletedQuizDTO>> getCompletedQuizzesByStudentId(
      @PathVariable Long studentId) {
    List<CompletedQuizDTO> completedQuizzes =
        studentService.getCompletedQuizzesByStudentId(studentId);
    return ResponseEntity.ok(completedQuizzes);
  }

  @PostMapping
  @Operation(summary = "Create a new student", description = "Creates a new student based on the provided details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the student"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data")
  })
  public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
    Student createdStudent = studentService.createStudent(studentDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Update a student", description = "Updates the existing student identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the student"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Not found - The student was not found")
  })
  public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                               @Valid @RequestBody StudentDTO studentDTO) {
    Student updatedStudent = studentService.updateStudent(id, studentDTO);
    return ResponseEntity.ok(updatedStudent);
  }


  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a student", description = "Deletes the student identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted the student"),
      @ApiResponse(responseCode = "404", description = "Not found - The student was not found")
  })
  public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok("Student deleted successfully.");
  }
}
