package com.onclass.reporte.infrastructure.input.rest;

import com.onclass.reporte.domain.exception.BootcampReportException;
import com.onclass.reporte.infrastructure.constants.ApiConstants;
import com.onclass.reporte.infrastructure.input.rest.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BootcampReportException.class)
    public ResponseEntity<ErrorResponse> handleBootcampReportException(BootcampReportException ex) {
        if (ApiConstants.REPORT_NOT_FOUND_CODE.equals(ex.getCode())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(WebExchangeBindException ex) {
        FieldError fieldError = ex.getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : ApiConstants.INVALID_REQUEST_MESSAGE;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }
}
