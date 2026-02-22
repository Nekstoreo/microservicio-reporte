package com.onclass.reporte.application.services.impl;

import com.onclass.reporte.application.dtos.requests.EnrollmentRequestData;
import com.onclass.reporte.application.dtos.requests.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import com.onclass.reporte.application.mappers.BootcampReportDtoMapper;
import com.onclass.reporte.application.services.BootcampReportService;
import com.onclass.reporte.domain.api.BootcampReportServicePort;
import com.onclass.reporte.domain.models.BootcampReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BootcampReportServiceImpl implements BootcampReportService {

    private final BootcampReportServicePort servicePort;

    public BootcampReportServiceImpl(BootcampReportServicePort servicePort) {
        this.servicePort = servicePort;
    }

    @Override
    public Mono<BootcampReportResponse> registerReport(RegisterBootcampReportRequest request) {
        BootcampReport report = BootcampReportDtoMapper.toModel(request);
        return servicePort.registerReport(report)
                .map(BootcampReportDtoMapper::toResponse);
    }

    @Override
    public Mono<Void> addEnrollment(String bootcampId, EnrollmentRequestData enrollment) {
        var enrollmentData = new BootcampReport.EnrollmentData(
                enrollment.getPersonId(),
                enrollment.getName(),
                enrollment.getEmail(),
                enrollment.getEnrollmentDate()
        );
        return servicePort.addEnrollment(bootcampId, enrollmentData);
    }

    @Override
    public Mono<BootcampReportResponse> getMostEnrolledBootcamp() {
        return servicePort.getMostEnrolledBootcamp()
                .map(BootcampReportDtoMapper::toResponse);
    }

    @Override
    public Mono<Page<BootcampReportResponse>> findAll(Pageable pageable) {
        return servicePort.findAll(pageable)
                .map(page -> page.map(BootcampReportDtoMapper::toResponse));
    }

    @Override
    public Mono<BootcampReportResponse> findById(String bootcampId) {
        return servicePort.findById(bootcampId)
                .map(BootcampReportDtoMapper::toResponse);
    }
}
