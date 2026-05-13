package com.onclass.reporte.domain.api;

import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.models.pagination.DomainPage;
import com.onclass.reporte.domain.models.pagination.DomainPageRequest;

public interface BootcampReportServicePort {

    BootcampReport registerReport(BootcampReport report);

    BootcampReport getMostEnrolledBootcamp();

    void addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment);

    DomainPage<BootcampReport> findAll(DomainPageRequest pageRequest);

    BootcampReport findById(String bootcampId);
}
