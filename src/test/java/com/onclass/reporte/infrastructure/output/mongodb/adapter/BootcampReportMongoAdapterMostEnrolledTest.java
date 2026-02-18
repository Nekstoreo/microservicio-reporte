package com.onclass.reporte.infrastructure.output.mongodb.adapter;

import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.infrastructure.output.mongodb.document.BootcampReportDocument;
import com.onclass.reporte.infrastructure.output.mongodb.document.EnrollmentDocument;
import com.onclass.reporte.infrastructure.output.mongodb.repository.BootcampReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampReportMongoAdapterMostEnrolledTest {

    @Mock
    private BootcampReportRepository repository;

    @InjectMocks
    private BootcampReportMongoAdapter adapter;

    @Test
    void should_find_bootcamp_with_most_enrollments() {
        var enrollment1 = new EnrollmentDocument(1L, "John", "john@example.com", LocalDateTime.now());
        var enrollment2 = new EnrollmentDocument(2L, "Jane", "jane@example.com", LocalDateTime.now());
        
        var doc1 = new BootcampReportDocument(
            "report-1", "1", "Java Bootcamp", "Learn Java",
            LocalDate.now(), 90, List.of(), List.of(),
            List.of(enrollment1, enrollment2),
            LocalDateTime.now(), LocalDateTime.now()
        );
        
        var doc2 = new BootcampReportDocument(
            "report-2", "2", "Python Bootcamp", "Learn Python",
            LocalDate.now(), 60, List.of(), List.of(),
            List.of(enrollment1),
            LocalDateTime.now(), LocalDateTime.now()
        );

        when(repository.findAll()).thenReturn(Flux.just(doc1, doc2));

        StepVerifier.create(adapter.findBootcampWithMostEnrollments())
            .expectNextMatches(report -> 
                report.getBootcampId().equals("1") && 
                report.getEnrollments().size() == 2
            )
            .verifyComplete();
    }

    @Test
    void should_return_empty_when_no_reports() {
        when(repository.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(adapter.findBootcampWithMostEnrollments())
            .verifyComplete();
    }
}
