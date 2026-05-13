package com.onclass.reporte.application.services.impl;

import com.onclass.reporte.application.dtos.requests.EnrollmentRequestData;
import com.onclass.reporte.application.dtos.requests.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import com.onclass.reporte.domain.api.BootcampReportServicePort;
import com.onclass.reporte.domain.models.BootcampReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampReportServiceImplTest {

    @Mock
    private BootcampReportServicePort servicePort;

    private BootcampReportServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BootcampReportServiceImpl(servicePort);
    }

    @Test
    void should_create_bootcamp_report_when_valid_request() {
        RegisterBootcampReportRequest request = new RegisterBootcampReportRequest(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, null, null, null
        );

        BootcampReport expectedReport = BootcampReport.create(
                request.getBootcampId(),
                request.getBootcampName(),
                request.getBootcampDescription(),
                request.getReleaseDate(),
                request.getDuration(),
                null,
                null,
                null
        );

        when(servicePort.registerReport(any(BootcampReport.class)))
                .thenReturn(expectedReport);

        var result = service.registerReport(request);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals("1", response.getBootcampId());
                    assertEquals("Java Bootcamp", response.getBootcampName());
                })
                .verifyComplete();
    }

    @Test
    void should_save_report_with_correct_data() {
        RegisterBootcampReportRequest request = new RegisterBootcampReportRequest(
                "2", "Frontend Bootcamp", "Learn React", LocalDate.of(2024, 4, 1),
                20, null, null, null
        );

        BootcampReport expectedReport = BootcampReport.create(
                request.getBootcampId(),
                request.getBootcampName(),
                request.getBootcampDescription(),
                request.getReleaseDate(),
                request.getDuration(),
                null,
                null,
                null
        );

        when(servicePort.registerReport(any(BootcampReport.class)))
                .thenReturn(expectedReport);

        var result = service.registerReport(request);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response.getReportId());
                    assertEquals("2", response.getBootcampId());
                })
                .verifyComplete();
    }

    @Test
    void should_add_enrollment_successfully() {
        String bootcampId = "1";
        EnrollmentRequestData enrollment = new EnrollmentRequestData(
                1L, "John Doe", "john@example.com", LocalDateTime.now()
        );

        doNothing().when(servicePort).addEnrollment(eq(bootcampId), any(BootcampReport.EnrollmentData.class));

        var result = service.addEnrollment(bootcampId, enrollment);

        StepVerifier.create(result).verifyComplete();
    }

    @Test
    void should_get_most_enrolled_bootcamp() {
        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, null, null, null
        );

        when(servicePort.getMostEnrolledBootcamp()).thenReturn(report);

        var result = service.getMostEnrolledBootcamp();

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals("1", response.getBootcampId());
                    assertEquals("Java Bootcamp", response.getBootcampName());
                })
                .verifyComplete();
    }
}
