package com.dopingtech.casestudy.quiz_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.dopingtech.casestudy.quiz_service.exception.OptionNotFoundException;
import com.dopingtech.casestudy.quiz_service.model.dto.OptionDTO;
import com.dopingtech.casestudy.quiz_service.model.entity.Option;
import com.dopingtech.casestudy.quiz_service.service.OptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class OptionControllerTest {

  @InjectMocks
  private OptionController optionController;

  @Mock
  private OptionService optionService;

  private OptionDTO optionDTO;
  private Option option;

  @BeforeEach
  public void setUp() {
    optionDTO = new OptionDTO();
    optionDTO.setLetter("A");
    optionDTO.setText("Option Text");
    optionDTO.setQuestionId(1L);
    option = new Option();
    option.setId(1L);
    option.setText(optionDTO.getText());
    option.setLetter(optionDTO.getLetter());
  }

  @Test
  public void getOptionById_shouldReturnOK() {
    when(optionService.findById(1L)).thenReturn(option);

    ResponseEntity<Option> response = optionController.getOptionById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void getOptionById_shouldReturnOption() {
    when(optionService.findById(1L)).thenReturn(option);

    ResponseEntity<Option> response = optionController.getOptionById(1L);

    assertEquals(option, response.getBody());
  }

  @Test
  public void getOptionById_shouldThrowOptionNotFoundException() {
    when(optionService.findById(1L)).thenThrow(new OptionNotFoundException(1L));

    assertThrows(OptionNotFoundException.class, () -> optionController.getOptionById(1L));
  }

  @Test
  public void createOption_shouldReturnCreated() {
    when(optionService.createOption(any(OptionDTO.class))).thenReturn(option);

    ResponseEntity<Option> response = optionController.createOption(optionDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void createOption_shouldReturnCreatedOption() {
    when(optionService.createOption(any(OptionDTO.class))).thenReturn(option);

    ResponseEntity<Option> response = optionController.createOption(optionDTO);

    assertEquals(option, response.getBody());
  }

  @Test
  public void createOption_shouldThrowOptionNotFoundException() {
    when(optionService.createOption(any(OptionDTO.class))).thenThrow(
        new OptionNotFoundException(1L));

    assertThrows(OptionNotFoundException.class, () -> optionController.createOption(optionDTO));
  }

  @Test
  public void updateOption_shouldReturnOk() {
    when(optionService.updateOption(eq(1L), any(OptionDTO.class))).thenReturn(option);

    ResponseEntity<Option> response = optionController.updateOption(1L, optionDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void updateOption_shouldReturnUpdatedOption() {
    when(optionService.updateOption(eq(1L), any(OptionDTO.class))).thenReturn(option);

    ResponseEntity<Option> response = optionController.updateOption(1L, optionDTO);

    assertEquals(option, response.getBody());
  }

  @Test
  public void updateOption_shouldThrowOptionNotFoundException() {
    when(optionService.updateOption(eq(1L), any(OptionDTO.class))).thenThrow(
        new OptionNotFoundException(1L));

    assertThrows(OptionNotFoundException.class, () -> optionController.updateOption(1L, optionDTO));
  }

  @Test
  public void deleteOption_shouldReturnOK() {
    doNothing().when(optionService).deleteOption(1L);

    ResponseEntity<String> response = optionController.deleteOption(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void deleteOption_shouldReturnSuccessMessage() {
    doNothing().when(optionService).deleteOption(1L);

    ResponseEntity<String> response = optionController.deleteOption(1L);

    assertEquals("Option deleted successfully.", response.getBody());
  }

  @Test
  public void deleteOption_shouldThrowOptionNotFoundException() {
    doThrow(new OptionNotFoundException(1L)).when(optionService).deleteOption(1L);

    assertThrows(OptionNotFoundException.class, () -> optionController.deleteOption(1L));
  }
}