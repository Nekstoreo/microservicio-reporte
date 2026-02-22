package com.onclass.reporte.application.mappers;

import com.onclass.reporte.application.dtos.requests.CapabilityRequestData;
import com.onclass.reporte.application.dtos.requests.EnrollmentRequestData;
import com.onclass.reporte.application.dtos.requests.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dtos.requests.TechnologyRequestData;
import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import com.onclass.reporte.domain.models.BootcampReport;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BootcampReportDtoMapperTest {

    @Test
    void toModel_shouldMapRequestWithNullCollections() {
        RegisterBootcampReportRequest request = new RegisterBootcampReportRequest(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, null, null, null
        );

        BootcampReport result = BootcampReportDtoMapper.toModel(request);

        assertNotNull(result.getReportId());
        assertEquals("1", result.getBootcampId());
        assertEquals("Java Bootcamp", result.getBootcampName());
        assertEquals("Learn Java", result.getBootcampDescription());
        assertEquals(LocalDate.of(2024, 3, 1), result.getReleaseDate());
        assertEquals(30, result.getDuration());
        assertNull(result.getCapabilities());
        assertNull(result.getTechnologies());
        assertNull(result.getEnrollments());
    }

    @Test
    void toModel_shouldMapRequestWithFullData() {
        TechnologyRequestData tech = new TechnologyRequestData(1L, "Java", "Programming language");
        CapabilityRequestData cap = new CapabilityRequestData(
                1L, "Backend", "Backend development", List.of(tech)
        );
        EnrollmentRequestData enr = new EnrollmentRequestData(
                1L, "John Doe", "john@example.com", LocalDateTime.now()
        );

        RegisterBootcampReportRequest request = new RegisterBootcampReportRequest(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, List.of(cap), List.of(tech), List.of(enr)
        );

        BootcampReport result = BootcampReportDtoMapper.toModel(request);

        assertEquals("1", result.getBootcampId());
        assertNotNull(result.getCapabilities());
        assertEquals(1, result.getCapabilities().size());
        assertEquals(1L, result.getCapabilities().get(0).getId());
        assertEquals("Backend", result.getCapabilities().get(0).getName());
        assertNotNull(result.getCapabilities().get(0).getTechnologies());
        assertEquals(1, result.getCapabilities().get(0).getTechnologies().size());

        assertNotNull(result.getTechnologies());
        assertEquals(1, result.getTechnologies().size());
        assertEquals("Java", result.getTechnologies().get(0).getName());

        assertNotNull(result.getEnrollments());
        assertEquals(1, result.getEnrollments().size());
        assertEquals("John Doe", result.getEnrollments().get(0).getName());
    }

    @Test
    void toResponse_shouldMapModelWithNullCollections() {
        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, null, null, null
        );

        BootcampReportResponse result = BootcampReportDtoMapper.toResponse(report);

        assertEquals(report.getReportId(), result.getReportId());
        assertEquals("1", result.getBootcampId());
        assertEquals("Java Bootcamp", result.getBootcampName());
        assertNull(result.getCapabilities());
        assertNull(result.getTechnologies());
        assertNull(result.getEnrollments());
        assertEquals(0, result.getCapabilityCount());
        assertEquals(0, result.getTechnologyCount());
        assertEquals(0, result.getEnrollmentCount());
    }

    @Test
    void toResponse_shouldMapModelWithFullData() {
        var tech = new BootcampReport.TechnologyData(1L, "Java", "Language");
        var cap = new BootcampReport.CapabilityData(1L, "Backend", "Backend dev", List.of(tech));
        var enr = new BootcampReport.EnrollmentData(1L, "John", "john@x.com", LocalDateTime.now());

        BootcampReport report = BootcampReport.create(
                "1", "Java Bootcamp", "Learn Java", LocalDate.of(2024, 3, 1),
                30, List.of(cap), List.of(tech), List.of(enr)
        );

        BootcampReportResponse result = BootcampReportDtoMapper.toResponse(report);

        assertNotNull(result.getCapabilities());
        assertEquals(1, result.getCapabilities().size());
        assertEquals("Backend", result.getCapabilities().get(0).getName());

        assertNotNull(result.getTechnologies());
        assertEquals(1, result.getTechnologies().size());

        assertNotNull(result.getEnrollments());
        assertEquals(1, result.getEnrollments().size());
        assertEquals("John", result.getEnrollments().get(0).getName());
    }
}
