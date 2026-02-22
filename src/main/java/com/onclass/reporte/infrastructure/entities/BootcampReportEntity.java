package com.onclass.reporte.infrastructure.entities;

import com.onclass.reporte.infrastructure.constants.ApiConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = ApiConstants.MONGODB_COLLECTION_NAME)
public class BootcampReportEntity {

    @Id
    private String reportId;
    private String bootcampId;
    private String bootcampName;
    private String bootcampDescription;
    private LocalDate releaseDate;
    private Integer duration;
    private List<CapabilityEntity> capabilities;
    private List<TechnologyEntity> technologies;
    private List<EnrollmentEntity> enrollments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BootcampReportEntity() {
    }

    public BootcampReportEntity(String reportId, String bootcampId, String bootcampName, String bootcampDescription,
                                LocalDate releaseDate, Integer duration, List<CapabilityEntity> capabilities,
                                List<TechnologyEntity> technologies, List<EnrollmentEntity> enrollments,
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

    public List<CapabilityEntity> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<CapabilityEntity> capabilities) {
        this.capabilities = capabilities;
    }

    public List<TechnologyEntity> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyEntity> technologies) {
        this.technologies = technologies;
    }

    public List<EnrollmentEntity> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentEntity> enrollments) {
        this.enrollments = enrollments;
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
