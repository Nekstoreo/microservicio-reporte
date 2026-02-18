package com.onclass.reporte.application.usecase;

import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import com.onclass.reporte.domain.exception.BootcampReportException;
import com.onclass.reporte.infrastructure.constants.ApiConstants;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AddEnrollmentToReportService {

    private final BootcampReportPersistencePort persistencePort;

    public AddEnrollmentToReportService(BootcampReportPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    public Mono<Void> execute(String bootcampId, BootcampReport.EnrollmentData enrollment) {
        return persistencePort.addEnrollment(bootcampId, enrollment)
            .switchIfEmpty(Mono.error(new BootcampReportException(ApiConstants.REPORT_NOT_FOUND_CODE, 
                ApiConstants.REPORT_NOT_FOUND_MESSAGE + bootcampId)))
            .then();
    }
}
