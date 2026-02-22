package com.onclass.reporte.infrastructure.output.mongodb.repository;

import com.onclass.reporte.infrastructure.output.mongodb.document.BootcampReportDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BootcampReportRepository extends ReactiveMongoRepository<BootcampReportDocument, String> {

    Mono<BootcampReportDocument> findByBootcampId(String bootcampId);
}
