package com.dopingtech.casestudy.quiz_service.controller;

import com.dopingtech.casestudy.quiz_service.model.dto.OptionDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/options")
@Tag(name = "Option API")
@Validated
public class OptionController {

  @Autowired
  private OptionService optionService;


  @GetMapping("/{id}")
  @Operation(summary = "Get an option by id", description = "Returns the option corresponding to the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the option"),
      @ApiResponse(responseCode = "404", description = "Not found - The option was not found")
  })
  public ResponseEntity<Option> getOptionById(@PathVariable Long id) {
    Option option = optionService.findById(id);
    return ResponseEntity.ok(option);
  }

  @PostMapping
  @Operation(summary = "Create a new option", description = "Creates a new option with the provided details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the option"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid option data"),
      @ApiResponse(responseCode = "404", description = "Not found - The question was not found provided id")
  })
  public ResponseEntity<Option> createOption(@Valid @RequestBody OptionDTO optionDTO) {
    Option createdOption = optionService.createOption(optionDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOption);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Update an option", description = "Updates the existing option identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the option"),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid option data"),
      @ApiResponse(responseCode = "404", description = "Not found - The option was not found")
  })
  public ResponseEntity<Option> updateOption(@PathVariable Long id,
                                             @Valid @RequestBody OptionDTO optionDTO) {
    Option updatedOption = optionService.updateOption(id, optionDTO);
    return ResponseEntity.ok(updatedOption);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an option", description = "Deletes the option identified by the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted the option"),
      @ApiResponse(responseCode = "404", description = "Not found - The option was not found")
  })
  public ResponseEntity<String> deleteOption(@PathVariable Long id) {
    optionService.deleteOption(id);
    return ResponseEntity.ok("Option deleted successfully.");
  }
}
