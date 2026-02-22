package com.onclass.reporte.domain.spi;

import com.onclass.reporte.domain.model.BootcampReport;
import reactor.core.publisher.Mono;

public interface BootcampReportPersistencePort {

    Mono<BootcampReport> save(BootcampReport report);

    Mono<BootcampReport> findByBootcampId(String bootcampId);

    Mono<BootcampReport> addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment);

    Mono<BootcampReport> findBootcampWithMostEnrollments();
}
