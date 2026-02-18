package com.onclass.reporte.application.usecase;

import com.onclass.reporte.application.dto.request.RegisterBootcampReportRequest;
import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterBootcampReportServiceTest {

    @Mock
    private BootcampReportPersistencePort persistencePort;

    private RegisterBootcampReportService service;

    @BeforeEach
    void setUp() {
        service = new RegisterBootcampReportService(persistencePort);
    }

    @Test
    void should_create_bootcamp_report_when_valid_request() {
        // Given
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

        when(persistencePort.save(any(BootcampReport.class)))
            .thenReturn(Mono.just(expectedReport));

        // When
        Mono<BootcampReport> result = service.execute(request);

        // Then
        StepVerifier.create(result)
            .assertNext(report -> {
                assertNotNull(report);
                assertEquals("1", report.getBootcampId());
                assertEquals("Java Bootcamp", report.getBootcampName());
            })
            .verifyComplete();
    }

    @Test
    void should_save_report_with_correct_data() {
        // Given
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

        when(persistencePort.save(any(BootcampReport.class)))
            .thenReturn(Mono.just(expectedReport));

        // When
        Mono<BootcampReport> result = service.execute(request);

        // Then
        StepVerifier.create(result)
            .assertNext(report -> {
                assertNotNull(report.getReportId());
                assertEquals("2", report.getBootcampId());
            })
            .verifyComplete();
    }
}
