package com.onclass.reporte.infrastructure.mappers;

import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.infrastructure.entities.BootcampReportEntity;
import com.onclass.reporte.infrastructure.entities.CapabilityEntity;
import com.onclass.reporte.infrastructure.entities.EnrollmentEntity;
import com.onclass.reporte.infrastructure.entities.TechnologyEntity;

public final class BootcampReportEntityMapper {

    private BootcampReportEntityMapper() {
        // Utility class - prevent instantiation
    }

    public static BootcampReportEntity toEntity(BootcampReport report) {
        var capabilityEntities = report.getCapabilities() != null
                ? report.getCapabilities().stream()
                .map(cap -> new CapabilityEntity(
                        cap.getId(),
                        cap.getName(),
                        cap.getDescription(),
                        cap.getTechnologies() != null
                                ? cap.getTechnologies().stream()
                                .map(tech -> new TechnologyEntity(
                                        tech.getId(),
                                        tech.getName(),
                                        tech.getDescription()
                                ))
                                .toList()
                                : null
                ))
                .toList()
                : null;

        var techEntities = report.getTechnologies() != null
                ? report.getTechnologies().stream()
                .map(tech -> new TechnologyEntity(
                        tech.getId(),
                        tech.getName(),
                        tech.getDescription()
                ))
                .toList()
                : null;

        var enrollEntities = report.getEnrollments() != null
                ? report.getEnrollments().stream()
                .map(enr -> new EnrollmentEntity(
                        enr.getPersonId(),
                        enr.getName(),
                        enr.getEmail(),
                        enr.getEnrollmentDate()
                ))
                .toList()
                : null;

        return new BootcampReportEntity(
                report.getReportId(),
                report.getBootcampId(),
                report.getBootcampName(),
                report.getBootcampDescription(),
                report.getReleaseDate(),
                report.getDuration(),
                capabilityEntities,
                techEntities,
                enrollEntities,
                report.getCreatedAt(),
                report.getUpdatedAt()
        );
    }

    public static BootcampReport toModel(BootcampReportEntity entity) {
        var capabilityModels = entity.getCapabilities() != null
                ? entity.getCapabilities().stream()
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

        var techModels = entity.getTechnologies() != null
                ? entity.getTechnologies().stream()
                .map(tech -> new BootcampReport.TechnologyData(
                        tech.getId(),
                        tech.getName(),
                        tech.getDescription()
                ))
                .toList()
                : null;

        var enrollModels = entity.getEnrollments() != null
                ? entity.getEnrollments().stream()
                .map(enr -> new BootcampReport.EnrollmentData(
                        enr.getPersonId(),
                        enr.getName(),
                        enr.getEmail(),
                        enr.getEnrollmentDate()
                ))
                .toList()
                : null;

        return BootcampReport.rehydrate(
                entity.getReportId(),
                entity.getBootcampId(),
                entity.getBootcampName(),
                entity.getBootcampDescription(),
                entity.getReleaseDate(),
                entity.getDuration(),
                capabilityModels,
                techModels,
                enrollModels,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
