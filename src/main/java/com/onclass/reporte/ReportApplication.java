package com.onclass.reporte;

import com.onclass.reporte.infrastructure.constants.ApiConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@OpenAPIDefinition(
        info = @Info(
                title = ApiConstants.API_TITLE,
                version = ApiConstants.API_VERSION,
                description = ApiConstants.API_DESCRIPTION
        )
)
@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "com.onclass.reporte.infrastructure.repositories")
public class ReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

}
