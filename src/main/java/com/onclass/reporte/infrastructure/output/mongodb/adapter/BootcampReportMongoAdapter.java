package com.onclass.reporte.infrastructure.output.mongodb.adapter;

import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import com.onclass.reporte.infrastructure.output.mongodb.document.BootcampReportDocument;
import com.onclass.reporte.infrastructure.output.mongodb.document.CapabilityDocument;
import com.onclass.reporte.infrastructure.output.mongodb.document.EnrollmentDocument;
import com.onclass.reporte.infrastructure.output.mongodb.document.TechnologyDocument;
import com.onclass.reporte.infrastructure.output.mongodb.repository.BootcampReportRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BootcampReportMongoAdapter implements BootcampReportPersistencePort {

    private final BootcampReportRepository repository;

    public BootcampReportMongoAdapter(BootcampReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<BootcampReport> save(BootcampReport report) {
        BootcampReportDocument document = toDocument(report);
        return repository.save(document)
            .map(this::toDomain);
    }

    @Override
    public Mono<BootcampReport> findByBootcampId(String bootcampId) {
        return repository.findByBootcampId(bootcampId)
            .map(this::toDomain);
    }

    @Override
    public Mono<BootcampReport> addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment) {
        return findByBootcampId(bootcampId)
            .flatMap(report -> {
                report.addEnrollment(enrollment);
                BootcampReportDocument document = toDocument(report);
                return repository.save(document)
                    .map(this::toDomain);
            });
    }

    private BootcampReportDocument toDocument(BootcampReport report) {
        var capabilityDocs = report.getCapabilities() != null
            ? report.getCapabilities().stream()
                .map(cap -> new CapabilityDocument(
                    cap.getId(),
                    cap.getName(),
                    cap.getDescription(),
                    cap.getTechnologies() != null
                        ? cap.getTechnologies().stream()
                            .map(tech -> new TechnologyDocument(
                                tech.getId(),
                                tech.getName(),
                                tech.getDescription()
                            ))
                            .collect(Collectors.toList())
                        : null
                ))
                .collect(Collectors.toList())
            : null;
        
        var techDocs = report.getTechnologies() != null
            ? report.getTechnologies().stream()
                .map(tech -> new TechnologyDocument(
                    tech.getId(),
                    tech.getName(),
                    tech.getDescription()
                ))
                .collect(Collectors.toList())
            : null;
        
        var enrollDocs = report.getEnrollments() != null
            ? report.getEnrollments().stream()
                .map(enr -> new EnrollmentDocument(
                    enr.getPersonId(),
                    enr.getName(),
                    enr.getEmail(),
                    enr.getEnrollmentDate()
                ))
                .collect(Collectors.toList())
            : null;
        
        return new BootcampReportDocument(
            report.getReportId(),
            report.getBootcampId(),
            report.getBootcampName(),
            report.getBootcampDescription(),
            report.getReleaseDate(),
            report.getDuration(),
            capabilityDocs,
            techDocs,
            enrollDocs,
            report.getCreatedAt(),
            report.getUpdatedAt()
        );
    }

    private BootcampReport toDomain(BootcampReportDocument document) {
        var capabilityModels = document.getCapabilities() != null
            ? document.getCapabilities().stream()
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
                            .collect(Collectors.toList())
                        : null
                ))
                .collect(Collectors.toList())
            : null;
        
        var techModels = document.getTechnologies() != null
            ? document.getTechnologies().stream()
                .map(tech -> new BootcampReport.TechnologyData(
                    tech.getId(),
                    tech.getName(),
                    tech.getDescription()
                ))
                .collect(Collectors.toList())
            : null;
        
        var enrollModels = document.getEnrollments() != null
            ? document.getEnrollments().stream()
                .map(enr -> new BootcampReport.EnrollmentData(
                    enr.getPersonId(),
                    enr.getName(),
                    enr.getEmail(),
                    enr.getEnrollmentDate()
                ))
                .collect(Collectors.toList())
            : null;
        
        return BootcampReport.rehydrate(
            document.getReportId(),
            document.getBootcampId(),
            document.getBootcampName(),
            document.getBootcampDescription(),
            document.getReleaseDate(),
            document.getDuration(),
            capabilityModels,
            techModels,
            enrollModels,
            document.getCreatedAt(),
            document.getUpdatedAt()
        );
    }
}
