package com.onclass.reporte.infrastructure.adapters.persistence;

import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.models.pagination.DomainPage;
import com.onclass.reporte.domain.models.pagination.DomainPageRequest;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import com.onclass.reporte.infrastructure.entities.BootcampReportEntity;
import com.onclass.reporte.infrastructure.mappers.BootcampReportEntityMapper;
import com.onclass.reporte.infrastructure.repositories.BootcampReportRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class BootcampReportPersistenceAdapter implements BootcampReportPersistencePort {

    private final BootcampReportRepository repository;

    public BootcampReportPersistenceAdapter(BootcampReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public BootcampReport save(BootcampReport report) {
        BootcampReportEntity entity = BootcampReportEntityMapper.toEntity(report);
        return repository.save(entity)
                .map(BootcampReportEntityMapper::toModel)
                .block();
    }

    @Override
    public BootcampReport findByBootcampId(String bootcampId) {
        return repository.findByBootcampId(bootcampId)
                .map(BootcampReportEntityMapper::toModel)
                .block();
    }

    @Override
    public BootcampReport addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment) {
        BootcampReport report = findByBootcampId(bootcampId);
        if (report == null) {
            return null;
        }
        report.addEnrollment(enrollment);
        BootcampReportEntity entity = BootcampReportEntityMapper.toEntity(report);
        return repository.save(entity)
                .map(BootcampReportEntityMapper::toModel)
                .block();
    }

    @Override
    public BootcampReport findBootcampWithMostEnrollments() {
        return repository.findAll()
                .collectList()
                .flatMap(reports -> {
                    if (reports.isEmpty()) {
                        return reactor.core.publisher.Mono.empty();
                    }
                    return reactor.core.publisher.Mono.just(reports.stream()
                            .max(Comparator.comparingInt(r ->
                                    r.getEnrollments() != null ? r.getEnrollments().size() : 0))
                            .orElseThrow());
                })
                .map(BootcampReportEntityMapper::toModel)
                .block();
    }

    @Override
    public DomainPage<BootcampReport> findAll(DomainPageRequest pageRequest) {
        var entities = repository.findAll()
                .collectList()
                .block();
        var models = entities.stream()
                .map(BootcampReportEntityMapper::toModel)
                .toList();
        int start = pageRequest.page() * pageRequest.size();
        int end = Math.min(start + pageRequest.size(), models.size());
        var content = start >= models.size() ? java.util.Collections.<BootcampReport>emptyList() : models.subList(start, end);
        int totalPages = models.isEmpty() ? 0 : (int) Math.ceil((double) models.size() / pageRequest.size());
        return new DomainPage<>(content, pageRequest.page(), pageRequest.size(), models.size(), totalPages, end < models.size(), pageRequest.page() > 0);
    }

    @Override
    public BootcampReport findById(String bootcampId) {
        return repository.findById(bootcampId)
                .map(BootcampReportEntityMapper::toModel)
                .block();
    }
}
