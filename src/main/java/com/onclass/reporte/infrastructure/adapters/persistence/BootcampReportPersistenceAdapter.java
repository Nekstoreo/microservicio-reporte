package com.onclass.reporte.infrastructure.adapters.persistence;

import com.onclass.reporte.domain.models.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import com.onclass.reporte.infrastructure.entities.BootcampReportEntity;
import com.onclass.reporte.infrastructure.mappers.BootcampReportEntityMapper;
import com.onclass.reporte.infrastructure.repositories.BootcampReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Component
public class BootcampReportPersistenceAdapter implements BootcampReportPersistencePort {

    private final BootcampReportRepository repository;

    public BootcampReportPersistenceAdapter(BootcampReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<BootcampReport> save(BootcampReport report) {
        BootcampReportEntity entity = BootcampReportEntityMapper.toEntity(report);
        return repository.save(entity)
                .map(BootcampReportEntityMapper::toModel);
    }

    @Override
    public Mono<BootcampReport> findByBootcampId(String bootcampId) {
        return repository.findByBootcampId(bootcampId)
                .map(BootcampReportEntityMapper::toModel);
    }

    @Override
    public Mono<BootcampReport> addEnrollment(String bootcampId, BootcampReport.EnrollmentData enrollment) {
        return findByBootcampId(bootcampId)
                .flatMap(report -> {
                    report.addEnrollment(enrollment);
                    BootcampReportEntity entity = BootcampReportEntityMapper.toEntity(report);
                    return repository.save(entity)
                            .map(BootcampReportEntityMapper::toModel);
                });
    }

    @Override
    public Mono<BootcampReport> findBootcampWithMostEnrollments() {
        return repository.findAll()
                .collectList()
                .flatMap(reports -> {
                    if (reports.isEmpty()) {
                        return Mono.empty();
                    }
                    return Mono.just(reports.stream()
                            .max(Comparator.comparingInt(r ->
                                    r.getEnrollments() != null ? r.getEnrollments().size() : 0))
                            .orElseThrow());
                })
                .map(BootcampReportEntityMapper::toModel);
    }

    @Override
    public Mono<Page<BootcampReport>> findAll(Pageable pageable) {
        return repository.findAll()
                .collectList()
                .map(entities -> {
                    var models = entities.stream()
                            .map(BootcampReportEntityMapper::toModel)
                            .toList();
                    int start = (int) pageable.getOffset();
                    int end = Math.min(start + pageable.getPageSize(), models.size());
                    var content = start >= models.size() ? java.util.Collections.<BootcampReport>emptyList() : models.subList(start, end);
                    return new org.springframework.data.domain.PageImpl<>(content, pageable, models.size());
                });
    }

    @Override
    public Mono<BootcampReport> findById(String bootcampId) {
        return repository.findById(bootcampId)
                .map(BootcampReportEntityMapper::toModel);
    }
}
