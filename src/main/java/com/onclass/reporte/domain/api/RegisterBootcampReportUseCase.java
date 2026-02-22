package com.onclass.reporte.domain.api;

import com.onclass.reporte.domain.model.BootcampReport;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RegisterBootcampReportUseCase {

    Mono<BootcampReport> execute(BootcampReportRequest request);

    interface BootcampReportRequest {
        String getBootcampId();
        String getBootcampName();
        String getBootcampDescription();
        LocalDate getReleaseDate();
        Integer getDuration();
        List<?> getCapabilities();
        List<?> getTechnologies();
        List<?> getEnrollments();
        Integer getCapabilityCount();
        Integer getTechnologyCount();
        Integer getEnrollmentCount();
    }
}
