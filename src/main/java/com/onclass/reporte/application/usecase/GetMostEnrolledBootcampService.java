package com.onclass.reporte.application.usecase;

import com.onclass.reporte.domain.api.GetMostEnrolledBootcampUseCase;
import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetMostEnrolledBootcampService implements GetMostEnrolledBootcampUseCase {

    private final BootcampReportPersistencePort persistencePort;

    @Override
    public Mono<BootcampReport> execute() {
        return persistencePort.findBootcampWithMostEnrollments();
    }
}
