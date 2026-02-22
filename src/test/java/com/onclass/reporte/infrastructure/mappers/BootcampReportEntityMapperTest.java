package com.onclass.reporte.infrastructure.mappers;

import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.infrastructure.entities.BootcampReportEntity;
import com.onclass.reporte.infrastructure.entities.CapabilityEntity;
import com.onclass.reporte.infrastructure.entities.EnrollmentEntity;
import com.onclass.reporte.infrastructure.entities.TechnologyEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BootcampReportEntityMapperTest {

    @Test
    void toEntity_shouldMapModelWithNullCollections() {
        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, null, null, null
        );

        BootcampReportEntity result = BootcampReportEntityMapper.toEntity(report);

        assertEquals(report.getReportId(), result.getReportId());
        assertEquals("1", result.getBootcampId());
        assertEquals("Java Bootcamp", result.getBootcampName());
        assertNull(result.getCapabilities());
        assertNull(result.getTechnologies());
        assertNull(result.getEnrollments());
    }

    @Test
    void toEntity_shouldMapModelWithFullData() {
        var tech = new BootcampReport.TechnologyData(1L, "Java", "Language");
        var cap = new BootcampReport.CapabilityData(1L, "Backend", "Backend", List.of(tech));
        var enr = new BootcampReport.EnrollmentData(1L, "John", "john@x.com", LocalDateTime.now());

        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, List.of(cap), List.of(tech), List.of(enr)
        );

        BootcampReportEntity result = BootcampReportEntityMapper.toEntity(report);

        assertNotNull(result.getCapabilities());
        assertEquals(1, result.getCapabilities().size());
        assertEquals("Backend", result.getCapabilities().get(0).getName());

        assertNotNull(result.getTechnologies());
        assertEquals(1, result.getTechnologies().size());

        assertNotNull(result.getEnrollments());
        assertEquals(1, result.getEnrollments().size());
    }

    @Test
    void toModel_shouldMapEntityWithNullCollections() {
        LocalDateTime now = LocalDateTime.now();
        BootcampReportEntity entity = new BootcampReportEntity(
                "report-1", "1", "Java Bootcamp", "Learn Java",
                LocalDate.of(2024, 3, 1), 30, null, null, null, now, now
        );

        BootcampReport result = BootcampReportEntityMapper.toModel(entity);

        assertEquals("report-1", result.getReportId());
        assertEquals("1", result.getBootcampId());
        assertNull(result.getCapabilities());
        assertNull(result.getTechnologies());
        assertNull(result.getEnrollments());
    }

    @Test
    void toModel_shouldMapEntityWithFullData() {
        LocalDateTime now = LocalDateTime.now();
        TechnologyEntity tech = new TechnologyEntity(1L, "Java", "Language");
        CapabilityEntity cap = new CapabilityEntity(1L, "Backend", "Backend", List.of(tech));
        EnrollmentEntity enr = new EnrollmentEntity(1L, "John", "john@x.com", now);

        BootcampReportEntity entity = new BootcampReportEntity(
                "report-1", "1", "Java Bootcamp", "Learn Java",
                LocalDate.of(2024, 3, 1), 30,
                List.of(cap), List.of(tech), List.of(enr),
                now, now
        );

        BootcampReport result = BootcampReportEntityMapper.toModel(entity);

        assertEquals("report-1", result.getReportId());
        assertNotNull(result.getCapabilities());
        assertEquals(1, result.getCapabilities().size());
        assertNotNull(result.getTechnologies());
        assertNotNull(result.getEnrollments());
        assertEquals("John", result.getEnrollments().get(0).getName());
    }

    @Test
    void toEntity_toModel_shouldRoundTrip() {
        var tech = new BootcampReport.TechnologyData(1L, "Java", "Lang");
        var cap = new BootcampReport.CapabilityData(1L, "Backend", "Desc", List.of(tech));
        var enr = new BootcampReport.EnrollmentData(1L, "John", "john@x.com", LocalDateTime.now());

        BootcampReport original = BootcampReport.create(
                "1", "Bootcamp", "Desc", LocalDate.now(), 90,
                List.of(cap), List.of(tech), List.of(enr)
        );

        BootcampReportEntity entity = BootcampReportEntityMapper.toEntity(original);
        BootcampReport restored = BootcampReportEntityMapper.toModel(entity);

        assertEquals(original.getBootcampId(), restored.getBootcampId());
        assertEquals(original.getBootcampName(), restored.getBootcampName());
        assertEquals(original.getCapabilities().size(), restored.getCapabilities().size());
    }
}
