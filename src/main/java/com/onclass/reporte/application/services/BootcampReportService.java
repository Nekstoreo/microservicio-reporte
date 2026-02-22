package com.onclass.reporte.application.services;

import com.onclass.reporte.application.dtos.requests.EnrollmentRequestData;
import com.onclass.reporte.application.dtos.requests.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BootcampReportService {

    Mono<BootcampReportResponse> registerReport(RegisterBootcampReportRequest request);

    Mono<Void> addEnrollment(String bootcampId, EnrollmentRequestData enrollment);

    Mono<BootcampReportResponse> getMostEnrolledBootcamp();

    Mono<Page<BootcampReportResponse>> findAll(Pageable pageable);

    Mono<BootcampReportResponse> findById(String bootcampId);
}
