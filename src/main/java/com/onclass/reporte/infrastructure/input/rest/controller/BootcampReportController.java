package com.onclass.reporte.infrastructure.input.rest.controller;

import com.onclass.reporte.application.dto.request.EnrollmentRequestData;
import com.onclass.reporte.application.dto.request.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dto.response.BootcampReportResponse;
import com.onclass.reporte.application.mapper.BootcampReportMapper;
import com.onclass.reporte.application.usecase.AddEnrollmentToReportService;
import com.onclass.reporte.application.usecase.GetMostEnrolledBootcampService;
import com.onclass.reporte.application.usecase.RegisterBootcampReportService;
import com.onclass.reporte.domain.model.BootcampReport;
import com.onclass.reporte.infrastructure.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstants.REPORTS_BOOTCAMPS_PATH)
@Tag(name = ApiConstants.OPENAPI_TAG_BOOTCAMP_REPORTS, description = ApiConstants.OPENAPI_TAG_DESCRIPTION)
public class BootcampReportController {

    private final RegisterBootcampReportService registerService;
    private final AddEnrollmentToReportService addEnrollmentService;
    private final GetMostEnrolledBootcampService getMostEnrolledService;

    public BootcampReportController(RegisterBootcampReportService registerService,
                                   AddEnrollmentToReportService addEnrollmentService,
                                   GetMostEnrolledBootcampService getMostEnrolledService) {
        this.registerService = registerService;
        this.addEnrollmentService = addEnrollmentService;
        this.getMostEnrolledService = getMostEnrolledService;
    }

    @PostMapping
    @Operation(
        summary = ApiConstants.OPENAPI_REGISTER_REPORT_SUMMARY,
        description = ApiConstants.OPENAPI_REGISTER_REPORT_DESCRIPTION
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = ApiConstants.OPENAPI_REGISTER_REPORT_201,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BootcampReportResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = ApiConstants.OPENAPI_REGISTER_REPORT_400
        ),
        @ApiResponse(
            responseCode = "401",
            description = ApiConstants.OPENAPI_REGISTER_REPORT_401
        )
    })
    public Mono<ResponseEntity<BootcampReportResponse>> registerReport(
            @RequestBody RegisterBootcampReportRequest request) {
        return registerService.execute(request)
            .map(BootcampReportMapper::toResponse)
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PostMapping("/{bootcampId}/enrollments")
    @Operation(
        summary = "Add enrollment to bootcamp report",
        description = "Adds a new enrollment to an existing bootcamp report"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Enrollment added successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Bootcamp report not found"
        )
    })
    public Mono<ResponseEntity<Void>> addEnrollment(
            @PathVariable 
            @Parameter(description = "Bootcamp ID", example = "1")
            String bootcampId,
            @RequestBody EnrollmentRequestData enrollment) {
        var enrollmentData = new BootcampReport.EnrollmentData(
            enrollment.getPersonId(),
            enrollment.getName(),
            enrollment.getEmail(),
            enrollment.getEnrollmentDate()
        );
        return addEnrollmentService.execute(bootcampId, enrollmentData)
            .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/most-enrolled")
    @Operation(
        summary = "Get bootcamp with most enrollments",
        description = "Returns the bootcamp with the highest number of enrolled persons"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bootcamp found successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BootcampReportResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No bootcamps found"
        )
    })
    public Mono<ResponseEntity<BootcampReportResponse>> getMostEnrolledBootcamp() {
        return getMostEnrolledService.execute()
            .map(BootcampReportMapper::toResponse)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}