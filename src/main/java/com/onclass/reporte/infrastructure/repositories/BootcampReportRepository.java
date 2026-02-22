package com.onclass.reporte.infrastructure.repositories;

import com.onclass.reporte.infrastructure.entities.BootcampReportEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BootcampReportRepository extends ReactiveMongoRepository<BootcampReportEntity, String> {

    Mono<BootcampReportEntity> findByBootcampId(String bootcampId);
}
