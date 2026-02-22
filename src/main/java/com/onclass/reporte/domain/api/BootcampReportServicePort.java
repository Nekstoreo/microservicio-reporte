package com.onclass.reporte.domain.api;

import com.onclass.reporte.domain.models.BootcampReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BootcampReportServicePort {

    Mono<BootcampReport> registerReport(BootcampReport report);

    Mono<BootcampReport> getMostEnrolledBootcamp();

    Mono<Void> addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment);

    Mono<Page<BootcampReport>> findAll(Pageable pageable);

    Mono<BootcampReport> findById(String bootcampId);
}
