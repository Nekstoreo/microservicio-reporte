package com.onclass.reporte.domain.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BootcampReportTest {

    @Test
    void create_shouldGenerateReportIdAndTimestamps() {
        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, null, null, null
        );

        assertNotNull(report.getReportId());
        assertFalse(report.getReportId().isEmpty());
        assertNotNull(report.getCreatedAt());
        assertNotNull(report.getUpdatedAt());
        assertEquals("1", report.getBootcampId());
        assertEquals("Java Bootcamp", report.getBootcampName());
    }

    @Test
    void rehydrate_shouldRestoreAllFields() {
        LocalDateTime now = LocalDateTime.now();
        var tech = new BootcampReport.TechnologyData(1L, "Java", "Lang");
        var cap = new BootcampReport.CapabilityData(1L, "Backend", "Desc", List.of(tech));
        var enr = new BootcampReport.EnrollmentData(1L, "John", "john@x.com", now);

        BootcampReport report = BootcampReport.rehydrate(
                "report-123", "1", "Java Bootcamp", "Learn Java",
                LocalDate.of(2024, 3, 1), 30,
                List.of(cap), List.of(tech), List.of(enr),
                now, now
        );

        assertEquals("report-123", report.getReportId());
        assertEquals("1", report.getBootcampId());
        assertEquals(1, report.getCapabilityCount());
        assertEquals(1, report.getTechnologyCount());
        assertEquals(1, report.getEnrollmentCount());
        assertEquals(now, report.getCreatedAt());
        assertEquals(now, report.getUpdatedAt());
    }

    @Test
    void getCapabilityCount_shouldReturnZero_whenNull() {
        BootcampReport report = BootcampReport.create(
                "1", "Bootcamp", "Desc", LocalDate.now(), 30,
                null, null, null
        );

        assertEquals(0, report.getCapabilityCount());
        assertEquals(0, report.getTechnologyCount());
        assertEquals(0, report.getEnrollmentCount());
    }

    @Test
    void addEnrollment_shouldCreateList_whenEnrollmentsNull() {
        BootcampReport report = BootcampReport.create(
                "1", "Bootcamp", "Desc", LocalDate.now(), 30,
                null, null, null
        );

        var enrollment = new BootcampReport.EnrollmentData(1L, "John", "john@x.com", LocalDateTime.now());
        report.addEnrollment(enrollment);

        assertNotNull(report.getEnrollments());
        assertEquals(1, report.getEnrollments().size());
        assertEquals("John", report.getEnrollments().get(0).getName());
        assertNotNull(report.getUpdatedAt());
    }

    @Test
    void addEnrollment_shouldAppend_whenEnrollmentsExist() {
        var enr1 = new BootcampReport.EnrollmentData(1L, "John", "john@x.com", LocalDateTime.now());
        BootcampReport report = BootcampReport.create(
                "1", "Bootcamp", "Desc", LocalDate.now(), 30,
                null, null, new ArrayList<>(List.of(enr1))
        );

        var enr2 = new BootcampReport.EnrollmentData(2L, "Jane", "jane@x.com", LocalDateTime.now());
        report.addEnrollment(enr2);

        assertEquals(2, report.getEnrollments().size());
        assertEquals("Jane", report.getEnrollments().get(1).getName());
    }

    @Test
    void capabilityData_gettersAndSetters() {
        var tech = new BootcampReport.TechnologyData(1L, "Java", "Lang");
        var cap = new BootcampReport.CapabilityData(1L, "Backend", "Desc", List.of(tech));

        assertEquals(1L, cap.getId());
        assertEquals("Backend", cap.getName());
        assertEquals("Desc", cap.getDescription());
        assertEquals(1, cap.getTechnologies().size());

        cap.setId(2L);
        cap.setName("Frontend");
        cap.setTechnologies(null);
        assertEquals(2L, cap.getId());
        assertEquals("Frontend", cap.getName());
        assertNull(cap.getTechnologies());
    }

    @Test
    void technologyData_gettersAndSetters() {
        var tech = new BootcampReport.TechnologyData(1L, "Java", "Lang");
        assertEquals(1L, tech.getId());
        assertEquals("Java", tech.getName());
        assertEquals("Lang", tech.getDescription());
    }

    @Test
    void enrollmentData_gettersAndSetters() {
        LocalDateTime now = LocalDateTime.now();
        var enr = new BootcampReport.EnrollmentData(1L, "John", "john@x.com", now);
        assertEquals(1L, enr.getPersonId());
        assertEquals("John", enr.getName());
        assertEquals("john@x.com", enr.getEmail());
        assertEquals(now, enr.getEnrollmentDate());
    }
}
