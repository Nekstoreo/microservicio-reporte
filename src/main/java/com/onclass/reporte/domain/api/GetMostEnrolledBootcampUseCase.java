package com.onclass.reporte.domain.api;

import com.onclass.reporte.domain.model.BootcampReport;
import reactor.core.publisher.Mono;

public interface GetMostEnrolledBootcampUseCase {
    Mono<BootcampReport> execute();
}
