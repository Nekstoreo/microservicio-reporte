package com.onclass.reporte.infrastructure.endpoints.rest;

import com.onclass.reporte.application.dtos.requests.EnrollmentRequestData;
import com.onclass.reporte.application.dtos.requests.RegisterBootcampReportRequest;
import com.onclass.reporte.application.dtos.responses.BootcampReportResponse;
import com.onclass.reporte.application.services.BootcampReportService;
import com.onclass.reporte.infrastructure.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstants.REPORTS_BOOTCAMPS_PATH)
@Tag(name = ApiConstants.OPENAPI_TAG_BOOTCAMP_REPORTS, description = ApiConstants.OPENAPI_TAG_DESCRIPTION)
public class BootcampReportController {

    private final BootcampReportService bootcampReportService;

    public BootcampReportController(BootcampReportService bootcampReportService) {
        this.bootcampReportService = bootcampReportService;
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
        return bootcampReportService.registerReport(request)
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
        return bootcampReportService.addEnrollment(bootcampId, enrollment)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping
    @Operation(
            summary = "List bootcamp reports",
            description = "Lists all bootcamp reports with pagination support"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Bootcamp reports listed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BootcampReportResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            )
    })
    public Mono<ResponseEntity<Page<BootcampReportResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bootcampName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return bootcampReportService.findAll(pageable)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{bootcampId}")
    @Operation(
            summary = "Get bootcamp report by ID",
            description = "Gets a bootcamp report by bootcamp ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Bootcamp report found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BootcampReportResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bootcamp report not found"
            )
    })
    public Mono<ResponseEntity<BootcampReportResponse>> getById(
            @PathVariable
            @Parameter(description = "Bootcamp ID", example = "1")
            String bootcampId) {
        return bootcampReportService.findById(bootcampId)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
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
        return bootcampReportService.getMostEnrolledBootcamp()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
