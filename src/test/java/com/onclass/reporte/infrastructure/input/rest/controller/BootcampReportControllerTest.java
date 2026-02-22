package com.onclass.reporte.infrastructure.input.rest.controller;

import com.onclass.reporte.application.dto.request.EnrollmentRequestData;
import com.onclass.reporte.application.dto.request.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dto.response.BootcampReportResponse;
import com.onclass.reporte.application.usecase.AddEnrollmentToReportService;
import com.onclass.reporte.application.usecase.RegisterBootcampReportService;
import com.onclass.reporte.domain.model.BootcampReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampReportControllerTest {

    @Mock
    private RegisterBootcampReportService registerService;

    @Mock
    private AddEnrollmentToReportService addEnrollmentService;

    private BootcampReportController controller;

    @BeforeEach
    void setUp() {
        controller = new BootcampReportController(registerService, addEnrollmentService);
    }

    @Test
    void should_register_report_and_return_created_status() {
        // Given
        RegisterBootcampReportRequest request = new RegisterBootcampReportRequest(
            "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
            30, null, null, null
        );

        BootcampReport report = BootcampReport.create(
            request.getBootcampId(),
            request.getBootcampName(),
            request.getBootcampDescription(),
            request.getReleaseDate(),
            request.getDuration(),
            null,
            null,
            null
        );

        when(registerService.execute(any(RegisterBootcampReportRequest.class)))
            .thenReturn(Mono.just(report));

        // When
        Mono<ResponseEntity<BootcampReportResponse>> responseMono = controller.registerReport(request);

        // Then
        StepVerifier.create(responseMono)
            .assertNext(response -> {
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals("1", response.getBody().getBootcampId());
            })
            .verifyComplete();
    }

    @Test
    void should_add_enrollment_and_return_no_content() {
        // Given
        String bootcampId = "1";
        EnrollmentRequestData enrollment = new EnrollmentRequestData(
            1L, "John Doe", "john@example.com", LocalDateTime.now()
        );

        when(addEnrollmentService.execute(eq(bootcampId), any(BootcampReport.EnrollmentData.class)))
            .thenReturn(Mono.empty());

        // When
        Mono<ResponseEntity<Void>> responseMono = controller.addEnrollment(bootcampId, enrollment);

        // Then
        StepVerifier.create(responseMono)
            .assertNext(response -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()))
            .verifyComplete();
    }
}
