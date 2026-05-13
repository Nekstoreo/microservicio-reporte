package com.onclass.reporte.infrastructure.adapters.persistence;

import com.onclass.reporte.infrastructure.entities.BootcampReportEntity;
import com.onclass.reporte.infrastructure.entities.EnrollmentEntity;
import com.onclass.reporte.infrastructure.repositories.BootcampReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampReportPersistenceAdapterMostEnrolledTest {

    @Mock
    private BootcampReportRepository repository;

    @InjectMocks
    private BootcampReportPersistenceAdapter adapter;

    @Test
    void should_find_bootcamp_with_most_enrollments() {
        var enrollment1 = new EnrollmentEntity(1L, "John", "john@example.com", LocalDateTime.now());
        var enrollment2 = new EnrollmentEntity(2L, "Jane", "jane@example.com", LocalDateTime.now());

        var entity1 = new BootcampReportEntity(
                "report-1", "1", "Java Bootcamp", "Learn Java",
                LocalDate.now(), 90, List.of(), List.of(),
                List.of(enrollment1, enrollment2),
                LocalDateTime.now(), LocalDateTime.now()
        );

        var entity2 = new BootcampReportEntity(
                "report-2", "2", "Python Bootcamp", "Learn Python",
                LocalDate.now(), 60, List.of(), List.of(),
                List.of(enrollment1),
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));

        var result = adapter.findBootcampWithMostEnrollments();

        assertNotNull(result);
        assertEquals("1", result.getBootcampId());
        assertEquals(2, result.getEnrollments().size());
    }

    @Test
    void should_return_null_when_no_reports() {
        when(repository.findAll()).thenReturn(Flux.empty());

        var result = adapter.findBootcampWithMostEnrollments();

        assertNull(result);
    }
}
