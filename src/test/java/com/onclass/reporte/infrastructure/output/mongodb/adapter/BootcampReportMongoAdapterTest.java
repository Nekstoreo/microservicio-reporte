package com.onclass.reporte.infrastructure.output.mongodb.adapter;

import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.infrastructure.output.mongodb.document.BootcampReportDocument;
import com.onclass.reporte.infrastructure.output.mongodb.repository.BootcampReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampReportMongoAdapterTest {

    @Mock
    private BootcampReportRepository repository;

    private BootcampReportMongoAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new BootcampReportMongoAdapter(repository);
    }

    @Test
    void should_save_bootcamp_report() {
        // Given
        BootcampReport report = BootcampReport.create(
            "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
            30, null, null, null
        );

        BootcampReportDocument document = new BootcampReportDocument(
            report.getReportId(),
            report.getBootcampId(),
            report.getBootcampName(),
            report.getBootcampDescription(),
            report.getReleaseDate(),
            report.getDuration(),
            null,
            null,
            null,
            report.getCreatedAt(),
            report.getUpdatedAt()
        );

        when(repository.save(any(BootcampReportDocument.class)))
            .thenReturn(Mono.just(document));

        // When
        var result = adapter.save(report);

        // Then
        StepVerifier.create(result)
            .assertNext(r -> {
                assertNotNull(r);
                assertEquals("1", r.getBootcampId());
                assertEquals("Java Bootcamp", r.getBootcampName());
            })
            .verifyComplete();
    }

    @Test
    void should_find_report_by_bootcamp_id() {
        // Given
        String bootcampId = "1";
        LocalDateTime now = LocalDateTime.now();
        BootcampReportDocument document = new BootcampReportDocument(
            "report-1", bootcampId, "Java Bootcamp", "Learn Java",
            LocalDate.of(2024, 3, 1), 30, null, null, null, now, now
        );

        when(repository.findByBootcampId(bootcampId))
            .thenReturn(Mono.just(document));

        // When
        var result = adapter.findByBootcampId(bootcampId);

        // Then
        StepVerifier.create(result)
            .assertNext(r -> {
                assertNotNull(r);
                assertEquals(bootcampId, r.getBootcampId());
            })
            .verifyComplete();
    }

    @Test
    void should_add_enrollment() {
        // Given
        String bootcampId = "1";
        LocalDateTime now = LocalDateTime.now();
        BootcampReport.EnrollmentData enrollment = new BootcampReport.EnrollmentData(
            1L, "John Doe", "john@example.com", now
        );
        
        BootcampReportDocument existingDoc = new BootcampReportDocument(
            "report-1", bootcampId, "Java Bootcamp", "Learn Java",
            LocalDate.of(2024, 3, 1), 30, null, null, null, now, now
        );

        BootcampReportDocument updatedDoc = new BootcampReportDocument(
            "report-1", bootcampId, "Java Bootcamp", "Learn Java",
            LocalDate.of(2024, 3, 1), 30, null, null, null, now, now
        );

        when(repository.findByBootcampId(bootcampId))
            .thenReturn(Mono.just(existingDoc));
        when(repository.save(any(BootcampReportDocument.class)))
            .thenReturn(Mono.just(updatedDoc));

        // When
        var result = adapter.addEnrollment(bootcampId, enrollment);

        // Then
        StepVerifier.create(result)
            .assertNext(r -> assertNotNull(r))
            .verifyComplete();
    }
}
