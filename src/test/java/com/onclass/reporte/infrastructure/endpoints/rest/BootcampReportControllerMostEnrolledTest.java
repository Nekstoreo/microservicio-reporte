package com.onclass.reporte.infrastructure.endpoints.rest;

import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import com.onclass.reporte.application.dtos.responses.EnrollmentResponseData;
import com.onclass.reporte.application.services.BootcampReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampReportControllerMostEnrolledTest {

    @Mock
    private BootcampReportService bootcampReportService;

    private BootcampReportController controller;

    @BeforeEach
    void setUp() {
        controller = new BootcampReportController(bootcampReportService);
    }

    @Test
    void should_return_bootcamp_with_most_enrollments() {
        var bootcampResponse = new BootcampReportResponse(
                "report-1", "1", "Java Bootcamp", "Learn Java",
                LocalDate.now(), 90, List.of(), List.of(),
                List.of(new EnrollmentResponseData(
                        1L, "John Doe", "john@example.com", LocalDateTime.now())),
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(bootcampReportService.getMostEnrolledBootcamp()).thenReturn(Mono.just(bootcampResponse));

        StepVerifier.create(controller.getMostEnrolledBootcamp())
                .assertNext(response -> {
                    assertEquals(200, response.getStatusCode().value());
                    assertEquals("1", response.getBody().getBootcampId());
                    assertEquals("Java Bootcamp", response.getBody().getBootcampName());
                })
                .verifyComplete();
    }

    @Test
    void should_return_404_when_no_bootcamps() {
        when(bootcampReportService.getMostEnrolledBootcamp()).thenReturn(Mono.empty());

        StepVerifier.create(controller.getMostEnrolledBootcamp())
                .assertNext(response -> assertEquals(404, response.getStatusCode().value()))
                .verifyComplete();
    }
}
