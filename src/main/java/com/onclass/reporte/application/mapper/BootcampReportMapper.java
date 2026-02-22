package com.onclass.reporte.application.mapper;

import com.onclass.reporte.application.dto.response.BootcampReportResponse;
import com.onclass.reporte.application.dto.response.CapabilityResponseData;
import com.onclass.reporte.application.dto.response.EnrollmentResponseData;
import com.onclass.reporte.application.dto.response.TechnologyResponseData;
import com.onclass.reporte.domain.model.BootcampReport;
import java.util.List;
import java.util.stream.Collectors;

public class BootcampReportMapper {

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
                            .collect(Collectors.toList())
                        : null
                ))
                .collect(Collectors.toList())
            : null;
        
        List<TechnologyResponseData> technologyDtos = report.getTechnologies() != null
            ? report.getTechnologies().stream()
                .map(tech -> new TechnologyResponseData(
                    tech.getId(),
                    tech.getName(),
                    tech.getDescription()
                ))
                .collect(Collectors.toList())
            : null;
        
        List<EnrollmentResponseData> enrollmentDtos = report.getEnrollments() != null
            ? report.getEnrollments().stream()
                .map(enr -> new EnrollmentResponseData(
                    enr.getPersonId(),
                    enr.getName(),
                    enr.getEmail(),
                    enr.getEnrollmentDate()
                ))
                .collect(Collectors.toList())
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
