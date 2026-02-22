package com.onclass.reporte.domain.usecases;

import com.onclass.reporte.domain.exceptions.BootcampReportException;
import com.onclass.reporte.domain.models.BootcampReport;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMostEnrolledBootcampUseCaseTest {

    @Mock
    private BootcampReportPersistencePort persistencePort;

    @InjectMocks
    private BootcampReportUseCase useCase;

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

        StepVerifier.create(useCase.getMostEnrolledBootcamp())
                .expectNext(bootcamp)
                .verifyComplete();
    }

    @Test
    void should_return_empty_when_no_bootcamps() {
        when(persistencePort.findBootcampWithMostEnrollments()).thenReturn(Mono.empty());

        StepVerifier.create(useCase.getMostEnrolledBootcamp())
                .verifyComplete();
    }

    @Test
    void should_register_report() {
        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.now(),
                30, null, null, null
        );

        when(persistencePort.save(any(BootcampReport.class))).thenReturn(Mono.just(report));

        StepVerifier.create(useCase.registerReport(report))
                .expectNext(report)
                .verifyComplete();
    }

    @Test
    void addEnrollment_shouldThrowException_whenReportNotFound() {
        String bootcampId = "999";
        BootcampReport.EnrollmentData enrollment = new BootcampReport.EnrollmentData(
                1L, "John", "john@example.com", LocalDateTime.now()
        );

        when(persistencePort.addEnrollment(eq(bootcampId), any(BootcampReport.EnrollmentData.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(useCase.addEnrollment(bootcampId, enrollment))
                .expectError(BootcampReportException.class)
                .verify();
    }

    @Test
    void addEnrollment_shouldComplete_whenReportExists() {
        String bootcampId = "1";
        BootcampReport.EnrollmentData enrollment = new BootcampReport.EnrollmentData(
                1L, "John", "john@example.com", LocalDateTime.now()
        );
        BootcampReport report = BootcampReport.create(
                bootcampId, "Java Bootcamp", "Learn Java", LocalDate.now(),
                30, null, null, List.of(enrollment)
        );

        when(persistencePort.addEnrollment(eq(bootcampId), any(BootcampReport.EnrollmentData.class)))
                .thenReturn(Mono.just(report));

        StepVerifier.create(useCase.addEnrollment(bootcampId, enrollment))
                .verifyComplete();
    }
}
