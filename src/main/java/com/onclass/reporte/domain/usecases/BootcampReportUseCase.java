package com.onclass.reporte.domain.usecases;

import com.onclass.reporte.domain.api.BootcampReportServicePort;
import com.onclass.reporte.domain.exceptions.BootcampReportException;
import com.onclass.reporte.domain.exceptions.BootcampReportExceptionCodes;
import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BootcampReportUseCase implements BootcampReportServicePort {

    private final BootcampReportPersistencePort persistencePort;

    public BootcampReportUseCase(BootcampReportPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<BootcampReport> registerReport(BootcampReport report) {
        return persistencePort.save(report);
    }

    @Override
    public Mono<BootcampReport> getMostEnrolledBootcamp() {
        return persistencePort.findBootcampWithMostEnrollments();
    }

    @Override
    public Mono<Void> addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment) {
        return persistencePort.addEnrollment(bootcampId, enrollment)
                .switchIfEmpty(Mono.error(new BootcampReportException(
                        BootcampReportExceptionCodes.REPORT_NOT_FOUND,
                        BootcampReportExceptionCodes.REPORT_NOT_FOUND_MESSAGE + bootcampId)))
                .then();
    }

    @Override
    public Mono<Page<BootcampReport>> findAll(Pageable pageable) {
        return persistencePort.findAll(pageable);
    }

    @Override
    public Mono<BootcampReport> findById(String bootcampId) {
        return persistencePort.findById(bootcampId)
                .switchIfEmpty(Mono.error(new BootcampReportException(
                        BootcampReportExceptionCodes.REPORT_NOT_FOUND,
                        BootcampReportExceptionCodes.REPORT_NOT_FOUND_MESSAGE + bootcampId)));
    }
}
