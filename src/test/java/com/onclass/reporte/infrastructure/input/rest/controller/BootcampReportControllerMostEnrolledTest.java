package com.onclass.reporte.infrastructure.input.rest.controller;

import com.onclass.reporte.application.usecase.GetMostEnrolledBootcampService;
import com.onclass.reporte.domain.model.BootcampReport;
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
    private GetMostEnrolledBootcampService getMostEnrolledService;

    @Mock
    private com.onclass.reporte.application.usecase.RegisterBootcampReportService registerService;

    @Mock
    private com.onclass.reporte.application.usecase.AddEnrollmentToReportService addEnrollmentService;

    private BootcampReportController controller;

    @BeforeEach
    void setUp() {
        controller = new BootcampReportController(registerService, addEnrollmentService, getMostEnrolledService);
    }

    @Test
    void should_return_bootcamp_with_most_enrollments() {
        var enrollment = new BootcampReport.EnrollmentData(1L, "John Doe", "john@example.com", LocalDateTime.now());
        var bootcamp = BootcampReport.rehydrate(
            "report-1", "1", "Java Bootcamp", "Learn Java",
            LocalDate.now(), 90, List.of(), List.of(),
            List.of(enrollment), LocalDateTime.now(), LocalDateTime.now()
        );

        when(getMostEnrolledService.execute()).thenReturn(Mono.just(bootcamp));

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
        when(getMostEnrolledService.execute()).thenReturn(Mono.empty());

        StepVerifier.create(controller.getMostEnrolledBootcamp())
            .assertNext(response -> assertEquals(404, response.getStatusCode().value()))
            .verifyComplete();
    }
}
