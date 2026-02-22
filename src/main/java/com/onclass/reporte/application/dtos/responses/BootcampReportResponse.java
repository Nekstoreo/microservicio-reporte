package com.onclass.reporte.application.dtos.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BootcampReportResponse {

    private String reportId;
    private String bootcampId;
    private String bootcampName;
    private String bootcampDescription;
    private LocalDate releaseDate;
    private Integer duration;
    private List<CapabilityResponseData> capabilities;
    private List<TechnologyResponseData> technologies;
    private List<EnrollmentResponseData> enrollments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BootcampReportResponse() {
    }

    public BootcampReportResponse(String reportId, String bootcampId, String bootcampName, String bootcampDescription,
                                  LocalDate releaseDate, Integer duration, List<CapabilityResponseData> capabilities,
                                  List<TechnologyResponseData> technologies, List<EnrollmentResponseData> enrollments,
                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reportId = reportId;
        this.bootcampId = bootcampId;
        this.bootcampName = bootcampName;
        this.bootcampDescription = bootcampDescription;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.capabilities = capabilities;
        this.technologies = technologies;
        this.enrollments = enrollments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(String bootcampId) {
        this.bootcampId = bootcampId;
    }

    public String getBootcampName() {
        return bootcampName;
    }

    public void setBootcampName(String bootcampName) {
        this.bootcampName = bootcampName;
    }

    public String getBootcampDescription() {
        return bootcampDescription;
    }

    public void setBootcampDescription(String bootcampDescription) {
        this.bootcampDescription = bootcampDescription;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<CapabilityResponseData> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<CapabilityResponseData> capabilities) {
        this.capabilities = capabilities;
    }

    public List<TechnologyResponseData> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyResponseData> technologies) {
        this.technologies = technologies;
    }

    public List<EnrollmentResponseData> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentResponseData> enrollments) {
        this.enrollments = enrollments;
    }

    public Integer getCapabilityCount() {
        return capabilities != null ? capabilities.size() : 0;
    }

    public Integer getTechnologyCount() {
        return technologies != null ? technologies.size() : 0;
    }

    public Integer getEnrollmentCount() {
        return enrollments != null ? enrollments.size() : 0;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
