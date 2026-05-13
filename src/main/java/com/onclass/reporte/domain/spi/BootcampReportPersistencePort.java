package com.onclass.reporte.domain.spi;

import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.models.pagination.DomainPage;
import com.onclass.reporte.domain.models.pagination.DomainPageRequest;

public interface BootcampReportPersistencePort {

    BootcampReport save(BootcampReport report);

    BootcampReport findByBootcampId(String bootcampId);

    BootcampReport addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment);

    BootcampReport findBootcampWithMostEnrollments();

    DomainPage<BootcampReport> findAll(DomainPageRequest pageRequest);

    BootcampReport findById(String bootcampId);
}
