package com.onclass.reporte.domain.usecases;

import com.onclass.reporte.domain.api.BootcampReportServicePort;
import com.onclass.reporte.domain.exceptions.BootcampReportException;
import com.onclass.reporte.domain.exceptions.BootcampReportExceptionCodes;
import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.models.pagination.DomainPage;
import com.onclass.reporte.domain.models.pagination.DomainPageRequest;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;

public class BootcampReportUseCase implements BootcampReportServicePort {

    private final BootcampReportPersistencePort persistencePort;

    public BootcampReportUseCase(BootcampReportPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public BootcampReport registerReport(BootcampReport report) {
        return persistencePort.save(report);
    }

    @Override
    public BootcampReport getMostEnrolledBootcamp() {
        return persistencePort.findBootcampWithMostEnrollments();
    }

    @Override
    public void addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment) {
        BootcampReport report = persistencePort.addEnrollment(bootcampId, enrollment);
        if (report == null) {
            throw new BootcampReportException(
                    BootcampReportExceptionCodes.REPORT_NOT_FOUND,
                    BootcampReportExceptionCodes.REPORT_NOT_FOUND_MESSAGE + bootcampId);
        }
    }

    @Override
    public DomainPage<BootcampReport> findAll(DomainPageRequest pageRequest) {
        return persistencePort.findAll(pageRequest);
    }

    @Override
    public BootcampReport findById(String bootcampId) {
        BootcampReport report = persistencePort.findById(bootcampId);
        if (report == null) {
            throw new BootcampReportException(
                    BootcampReportExceptionCodes.REPORT_NOT_FOUND,
                    BootcampReportExceptionCodes.REPORT_NOT_FOUND_MESSAGE + bootcampId);
        }
        return report;
    }
}
