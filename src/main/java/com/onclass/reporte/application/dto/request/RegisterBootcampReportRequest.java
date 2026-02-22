package com.onclass.reporte.application.dto.request;

import java.time.LocalDate;
import java.util.List;

public class RegisterBootcampReportRequest implements com.onclass.reporte.domain.api.RegisterBootcampReportUseCase.BootcampReportRequest {

    private String bootcampId;
    private String bootcampName;
    private String bootcampDescription;
    private LocalDate releaseDate;
    private Integer duration;
    private List<CapabilityRequestData> capabilities;
    private List<TechnologyRequestData> technologies;
    private List<EnrollmentRequestData> enrollments;

    public RegisterBootcampReportRequest() {
    }

    public RegisterBootcampReportRequest(String bootcampId, String bootcampName, String bootcampDescription,
                                        LocalDate releaseDate, Integer duration, List<CapabilityRequestData> capabilities,
                                        List<TechnologyRequestData> technologies, List<EnrollmentRequestData> enrollments) {
        this.bootcampId = bootcampId;
        this.bootcampName = bootcampName;
        this.bootcampDescription = bootcampDescription;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.capabilities = capabilities;
        this.technologies = technologies;
        this.enrollments = enrollments;
    }

    @Override
    public String getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(String bootcampId) {
        this.bootcampId = bootcampId;
    }

    @Override
    public String getBootcampName() {
        return bootcampName;
    }

    public void setBootcampName(String bootcampName) {
        this.bootcampName = bootcampName;
    }

    @Override
    public String getBootcampDescription() {
        return bootcampDescription;
    }

    public void setBootcampDescription(String bootcampDescription) {
        this.bootcampDescription = bootcampDescription;
    }

    @Override
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<CapabilityRequestData> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<CapabilityRequestData> capabilities) {
        this.capabilities = capabilities;
    }

    public List<TechnologyRequestData> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyRequestData> technologies) {
        this.technologies = technologies;
    }

    public List<EnrollmentRequestData> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentRequestData> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public Integer getCapabilityCount() {
        return capabilities != null ? capabilities.size() : 0;
    }

    @Override
    public Integer getTechnologyCount() {
        return technologies != null ? technologies.size() : 0;
    }

    @Override
    public Integer getEnrollmentCount() {
        return enrollments != null ? enrollments.size() : 0;
    }
}
