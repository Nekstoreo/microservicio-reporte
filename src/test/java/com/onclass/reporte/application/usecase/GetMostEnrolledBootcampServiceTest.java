package com.onclass.reporte.application.usecase;

import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMostEnrolledBootcampServiceTest {

    @Mock
    private BootcampReportPersistencePort persistencePort;

    @InjectMocks
    private GetMostEnrolledBootcampService service;

    @Test
    void should_return_bootcamp_with_most_enrollments() {
        var enrollment1 = new BootcampReport.EnrollmentData(1L, "John Doe", "john@example.com", LocalDateTime.now());
        var enrollment2 = new BootcampReport.EnrollmentData(2L, "Jane Doe", "jane@example.com", LocalDateTime.now());
        
        var bootcamp = BootcampReport.rehydrate(
            "report-1",
            "1",
            "Java Bootcamp",
            "Learn Java",
            LocalDate.now(),
            90,
            List.of(),
            List.of(),
            List.of(enrollment1, enrollment2),
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        when(persistencePort.findBootcampWithMostEnrollments()).thenReturn(Mono.just(bootcamp));

        StepVerifier.create(service.execute())
            .expectNext(bootcamp)
            .verifyComplete();
    }

    @Test
    void should_return_empty_when_no_bootcamps() {
        when(persistencePort.findBootcampWithMostEnrollments()).thenReturn(Mono.empty());

        StepVerifier.create(service.execute())
            .verifyComplete();
    }
}
