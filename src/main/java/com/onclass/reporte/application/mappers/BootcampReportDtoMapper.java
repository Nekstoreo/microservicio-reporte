package com.onclass.reporte.application.mappers;

import com.onclass.reporte.application.dtos.requests.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import com.onclass.reporte.application.dtos.responses.CapabilityResponseData;
import com.onclass.reporte.application.dtos.responses.EnrollmentResponseData;
import com.onclass.reporte.application.dtos.responses.TechnologyResponseData;
import com.onclass.reporte.domain.models.BootcampReport;

import java.util.List;

public final class BootcampReportDtoMapper {

    private BootcampReportDtoMapper() {
        // Utility class - prevent instantiation
    }

    public static BootcampReport toModel(RegisterBootcampReportRequest request) {
        var capabilitiesData = request.getCapabilities() != null
                ? request.getCapabilities().stream()
                .map(cap -> new BootcampReport.CapabilityData(
                        cap.getId(),
                        cap.getName(),
                        cap.getDescription(),
                        cap.getTechnologies() != null
                                ? cap.getTechnologies().stream()
                                .map(tech -> new BootcampReport.TechnologyData(
                                        tech.getId(),
                                        tech.getName(),
                                        tech.getDescription()
                                ))
                                .toList()
                                : null
                ))
                .toList()
                : null;

        var technologiesData = request.getTechnologies() != null
                ? request.getTechnologies().stream()
                .map(tech -> new BootcampReport.TechnologyData(
                        tech.getId(),
                        tech.getName(),
                        tech.getDescription()
                ))
                .toList()
                : null;

        var enrollmentsData = request.getEnrollments() != null
                ? request.getEnrollments().stream()
                .map(enr -> new BootcampReport.EnrollmentData(
                        enr.getPersonId(),
                        enr.getName(),
                        enr.getEmail(),
                        enr.getEnrollmentDate()
                ))
                .toList()
                : null;

        return BootcampReport.create(
                request.getBootcampId(),
                request.getBootcampName(),
                request.getBootcampDescription(),
                request.getReleaseDate(),
                request.getDuration(),
                capabilitiesData,
                technologiesData,
                enrollmentsData
        );
    }

    public static BootcampReportResponse toResponse(BootcampReport report) {
        List<CapabilityResponseData> capabilityDtos = report.getCapabilities() != null
                ? report.getCapabilities().stream()
                .map(cap -> new CapabilityResponseData(
                        cap.getId(),
                        cap.getName(),
                        cap.getDescription(),
                        cap.getTechnologies() != null
                                ? cap.getTechnologies().stream()
                                .map(tech -> new TechnologyResponseData(
                                        tech.getId(),
                                        tech.getName(),
                                        tech.getDescription()
                                ))
                                .toList()
                                : null
                ))
                .toList()
                : null;

        List<TechnologyResponseData> technologyDtos = report.getTechnologies() != null
                ? report.getTechnologies().stream()
                .map(tech -> new TechnologyResponseData(
                        tech.getId(),
                        tech.getName(),
                        tech.getDescription()
                ))
                .toList()
                : null;

        List<EnrollmentResponseData> enrollmentDtos = report.getEnrollments() != null
                ? report.getEnrollments().stream()
                .map(enr -> new EnrollmentResponseData(
                        enr.getPersonId(),
                        enr.getName(),
                        enr.getEmail(),
                        enr.getEnrollmentDate()
                ))
                .toList()
                : null;

        return new BootcampReportResponse(
                report.getReportId(),
                report.getBootcampId(),
                report.getBootcampName(),
                report.getBootcampDescription(),
                report.getReleaseDate(),
                report.getDuration(),
                capabilityDtos,
                technologyDtos,
                enrollmentDtos,
                report.getCreatedAt(),
                report.getUpdatedAt()
        );
    }
}
