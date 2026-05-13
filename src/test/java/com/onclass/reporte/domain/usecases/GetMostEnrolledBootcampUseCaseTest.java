package com.onclass.reporte.domain.usecases;

import com.onclass.reporte.domain.exceptions.BootcampReportException;
import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        when(persistencePort.findBootcampWithMostEnrollments()).thenReturn(bootcamp);

        BootcampReport result = useCase.getMostEnrolledBootcamp();

        assertNotNull(result);
        assertEquals("1", result.getBootcampId());
        assertEquals(2, result.getEnrollments().size());
    }

    @Test
    void should_return_null_when_no_bootcamps() {
        when(persistencePort.findBootcampWithMostEnrollments()).thenReturn(null);

        BootcampReport result = useCase.getMostEnrolledBootcamp();

        assertNull(result);
    }

    @Test
    void should_register_report() {
        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.now(),
                30, null, null, null
        );

        when(persistencePort.save(any(BootcampReport.class))).thenReturn(report);

        BootcampReport result = useCase.registerReport(report);

        assertNotNull(result);
        assertEquals("1", result.getBootcampId());
    }

    @Test
    void addEnrollment_shouldThrowException_whenReportNotFound() {
        String bootcampId = "999";
        BootcampReport.EnrollmentData enrollment = new BootcampReport.EnrollmentData(
                1L, "John", "john@example.com", LocalDateTime.now()
        );

        when(persistencePort.addEnrollment(eq(bootcampId), any(BootcampReport.EnrollmentData.class)))
                .thenReturn(null);

        assertThrows(BootcampReportException.class, () -> useCase.addEnrollment(bootcampId, enrollment));
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
                .thenReturn(report);

        assertDoesNotThrow(() -> useCase.addEnrollment(bootcampId, enrollment));
    }
}
