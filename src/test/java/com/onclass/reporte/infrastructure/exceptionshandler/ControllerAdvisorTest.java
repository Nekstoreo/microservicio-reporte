package com.onclass.reporte.infrastructure.exceptionshandler;

import com.onclass.reporte.domain.exceptions.BootcampReportException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ControllerAdvisorTest {

    @InjectMocks
    private GlobalExceptionHandler controllerAdvisor;

    @Test
    void handleBootcampReportException_shouldReturn404_whenReportNotFound() {
        BootcampReportException ex = new BootcampReportException(
                BootcampReportExceptionConstant.REPORT_NOT_FOUND_CODE,
                "Report not found for bootcamp: 123"
        );

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> result =
                controllerAdvisor.handleBootcampReportException(ex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Report not found for bootcamp: 123", result.getBody().message());
    }

    @Test
    void handleBootcampReportException_shouldReturn400_whenOtherError() {
        BootcampReportException ex = new BootcampReportException(
                "REPORT_ERROR",
                "Invalid report data"
        );

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> result =
                controllerAdvisor.handleBootcampReportException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Invalid report data", result.getBody().message());
    }

    @Test
    void handleBindException_shouldReturn400_withFieldError() {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "object");
        bindingResult.rejectValue("bootcampId", "required", "Bootcamp ID is required");
        WebExchangeBindException ex = new WebExchangeBindException(null, bindingResult);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> result =
                controllerAdvisor.handleBindException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Bootcamp ID is required", result.getBody().message());
    }

    @Test
    void handleBindException_shouldReturnInvalidRequest_whenNoFieldError() {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "object");
        WebExchangeBindException ex = new WebExchangeBindException(null, bindingResult);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> result =
                controllerAdvisor.handleBindException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(BootcampReportExceptionConstant.INVALID_REQUEST_MESSAGE, result.getBody().message());
    }
}
