package com.onclass.reporte.domain.spi;

import com.onclass.reporte.domain.models.BootcampReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BootcampReportPersistencePort {

    Mono<BootcampReport> save(BootcampReport report);

    Mono<BootcampReport> findByBootcampId(String bootcampId);

    Mono<BootcampReport> addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment);

    Mono<BootcampReport> findBootcampWithMostEnrollments();

    Mono<Page<BootcampReport>> findAll(Pageable pageable);

    Mono<BootcampReport> findById(String bootcampId);
}
