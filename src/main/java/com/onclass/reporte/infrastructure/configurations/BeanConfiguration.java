package com.onclass.reporte.infrastructure.configurations;

import com.onclass.reporte.domain.api.BootcampReportServicePort;
import com.onclass.reporte.domain.spi.BootcampReportPersistencePort;
import com.onclass.reporte.domain.usecases.BootcampReportUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public BootcampReportServicePort bootcampReportServicePort(BootcampReportPersistencePort persistencePort) {
        return new BootcampReportUseCase(persistencePort);
    }
}
