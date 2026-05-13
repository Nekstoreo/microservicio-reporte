package com.onclass.reporte.application.services.impl;

import com.onclass.reporte.application.dtos.requests.EnrollmentRequestData;
import com.onclass.reporte.application.dtos.requests.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import com.onclass.reporte.application.mappers.BootcampReportDtoMapper;
import com.onclass.reporte.application.services.BootcampReportService;
import com.onclass.reporte.domain.api.BootcampReportServicePort;
import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.models.pagination.DomainPage;
import com.onclass.reporte.domain.models.pagination.DomainPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        return Mono.fromCallable(() -> {
            BootcampReport report = BootcampReportDtoMapper.toModel(request);
            BootcampReport result = servicePort.registerReport(report);
            return BootcampReportDtoMapper.toResponse(result);
        });
    }

    @Override
    public Mono<Void> addEnrollment(String bootcampId, EnrollmentRequestData enrollment) {
        return Mono.fromRunnable(() -> {
            var enrollmentData = new BootcampReport.EnrollmentData(
                    enrollment.getPersonId(),
                    enrollment.getName(),
                    enrollment.getEmail(),
                    enrollment.getEnrollmentDate()
            );
            servicePort.addEnrollment(bootcampId, enrollmentData);
        });
    }

    @Override
    public Mono<BootcampReportResponse> getMostEnrolledBootcamp() {
        return Mono.fromCallable(() -> {
            BootcampReport result = servicePort.getMostEnrolledBootcamp();
            return result != null ? BootcampReportDtoMapper.toResponse(result) : null;
        });
    }

    @Override
    public Mono<Page<BootcampReportResponse>> findAll(Pageable pageable) {
        return Mono.fromCallable(() -> {
            DomainPageRequest pageRequest = new DomainPageRequest(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getProperty() : "bootcampName",
                    pageable.getSort().isSorted() && pageable.getSort().iterator().next().isDescending() ? "desc" : "asc"
            );
            DomainPage<BootcampReport> page = servicePort.findAll(pageRequest);
            return new PageImpl<>(
                    page.content().stream().map(BootcampReportDtoMapper::toResponse).toList(),
                    pageable,
                    page.totalElements()
            );
        });
    }

    @Override
    public Mono<BootcampReportResponse> findById(String bootcampId) {
        return Mono.fromCallable(() -> {
            BootcampReport result = servicePort.findById(bootcampId);
            return BootcampReportDtoMapper.toResponse(result);
        });
    }
}
