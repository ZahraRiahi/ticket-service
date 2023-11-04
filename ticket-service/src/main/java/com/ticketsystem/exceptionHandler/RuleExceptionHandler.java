package com.ticketsystem.exceptionHandler;

import com.ticketsystem.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RuleExceptionHandler {

    @ExceptionHandler(RuleException.class)
    public final ResponseEntity<ErrorDTO> handleNotFoundException(RuleException ruleException) {
        ErrorDTO error = new ErrorDTO(ruleException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
