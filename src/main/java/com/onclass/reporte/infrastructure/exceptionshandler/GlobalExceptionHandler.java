package com.onclass.reporte.infrastructure.exceptionshandler;

import com.onclass.reporte.domain.exceptions.BootcampReportException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BootcampReportException.class)
    public ResponseEntity<ErrorResponse> handleBootcampReportException(BootcampReportException ex, ServerWebExchange exchange) {
        if (BootcampReportExceptionConstant.REPORT_NOT_FOUND_CODE.equals(ex.getCode())) {
            return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), exchange);
        }
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), exchange);
    }
    public ResponseEntity<ErrorResponse> handleBootcampReportException(BootcampReportException ex) {
        return handleBootcampReportException(ex, null);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(WebExchangeBindException ex, ServerWebExchange exchange) {
        FieldError fieldError = ex.getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : BootcampReportExceptionConstant.INVALID_REQUEST_MESSAGE;
        return buildError(HttpStatus.BAD_REQUEST, message, exchange);
    }
    public ResponseEntity<ErrorResponse> handleBindException(WebExchangeBindException ex) {
        return handleBindException(ex, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, ServerWebExchange exchange) {
        System.err.println("--- UNEXPECTED EXCEPTION OCCURRED ---");
        ex.printStackTrace();
        System.err.println("-------------------------------------");
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage(), exchange);
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message, ServerWebExchange exchange) {
        String path = exchange != null ? exchange.getRequest().getPath().value() : null;
        String requestId = exchange != null ? exchange.getRequest().getId() : null;
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                requestId
        ));
    }

    public record ErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path,
            String requestId
    ) {
        public ErrorResponse(String message) {
            this(Instant.now(), 0, null, message, null, null);
        }
    }
}
