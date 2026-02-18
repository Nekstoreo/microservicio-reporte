package com.onclass.reporte.application.usecase;

import com.onclass.reporte.application.dto.request.CapabilityRequestData;
import com.onclass.reporte.application.dto.request.EnrollmentRequestData;
import com.onclass.reporte.application.dto.request.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dto.request.TechnologyRequestData;
import com.onclass.reporte.domain.api.RegisterBootcampReportUseCase;
import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.stream.Collectors;

@Service
public class RegisterBootcampReportService implements RegisterBootcampReportUseCase {

    private final BootcampReportPersistencePort persistencePort;

    public RegisterBootcampReportService(BootcampReportPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<BootcampReport> execute(BootcampReportRequest request) {
        RegisterBootcampReportRequest concreteRequest = (RegisterBootcampReportRequest) request;
        
        var capabilitiesData = concreteRequest.getCapabilities() != null 
            ? concreteRequest.getCapabilities().stream()
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
        
        var technologiesData = concreteRequest.getTechnologies() != null
            ? concreteRequest.getTechnologies().stream()
                .map(tech -> new BootcampReport.TechnologyData(
                    tech.getId(),
                    tech.getName(),
                    tech.getDescription()
                ))
                .collect(Collectors.toList())
            : null;
        
        var enrollmentsData = concreteRequest.getEnrollments() != null
            ? concreteRequest.getEnrollments().stream()
                .map(enr -> new BootcampReport.EnrollmentData(
                    enr.getPersonId(),
                    enr.getName(),
                    enr.getEmail(),
                    enr.getEnrollmentDate()
                ))
                .collect(Collectors.toList())
            : null;
        
        BootcampReport report = BootcampReport.create(
            concreteRequest.getBootcampId(),
            concreteRequest.getBootcampName(),
            concreteRequest.getBootcampDescription(),
            concreteRequest.getReleaseDate(),
            concreteRequest.getDuration(),
            capabilitiesData,
            technologiesData,
            enrollmentsData
        );

        return persistencePort.save(report);
    }
}
