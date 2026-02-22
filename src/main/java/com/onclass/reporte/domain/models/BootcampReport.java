package com.onclass.reporte.domain.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BootcampReport {

    private String reportId;
    private String bootcampId;
    private String bootcampName;
    private String bootcampDescription;
    private LocalDate releaseDate;
    private Integer duration;
    private List<CapabilityData> capabilities;
    private List<TechnologyData> technologies;
    private List<EnrollmentData> enrollments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private BootcampReport() {
    }

    public static BootcampReport create(String bootcampId, String bootcampName, String bootcampDescription,
                                        LocalDate releaseDate, Integer duration,
                                        List<CapabilityData> capabilities, List<TechnologyData> technologies,
                                        List<EnrollmentData> enrollments) {
        BootcampReport report = new BootcampReport();
        report.reportId = UUID.randomUUID().toString();
        report.bootcampId = bootcampId;
        report.bootcampName = bootcampName;
        report.bootcampDescription = bootcampDescription;
        report.releaseDate = releaseDate;
        report.duration = duration;
        report.capabilities = capabilities;
        report.technologies = technologies;
        report.enrollments = enrollments;
        report.createdAt = LocalDateTime.now();
        report.updatedAt = LocalDateTime.now();
        return report;
    }

    public static BootcampReport rehydrate(String reportId, String bootcampId, String bootcampName,
                                           String bootcampDescription, LocalDate releaseDate, Integer duration,
                                           List<CapabilityData> capabilities, List<TechnologyData> technologies,
                                           List<EnrollmentData> enrollments, LocalDateTime createdAt,
                                           LocalDateTime updatedAt) {
        BootcampReport report = new BootcampReport();
        report.reportId = reportId;
        report.bootcampId = bootcampId;
        report.bootcampName = bootcampName;
        report.bootcampDescription = bootcampDescription;
        report.releaseDate = releaseDate;
        report.duration = duration;
        report.capabilities = capabilities;
        report.technologies = technologies;
        report.enrollments = enrollments;
        report.createdAt = createdAt;
        report.updatedAt = updatedAt;
        return report;
    }

    public String getReportId() {
        return reportId;
    }

    public String getBootcampId() {
        return bootcampId;
    }

    public String getBootcampName() {
        return bootcampName;
    }

    public String getBootcampDescription() {
        return bootcampDescription;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public List<CapabilityData> getCapabilities() {
        return capabilities;
    }

    public List<TechnologyData> getTechnologies() {
        return technologies;
    }

    public List<EnrollmentData> getEnrollments() {
        return enrollments;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void addEnrollment(EnrollmentData enrollment) {
        if (this.enrollments == null) {
            this.enrollments = new ArrayList<>();
        } else if (!(this.enrollments instanceof ArrayList)) {
            this.enrollments = new ArrayList<>(this.enrollments);
        }
        this.enrollments.add(enrollment);
        this.updatedAt = LocalDateTime.now();
    }

    // Nested classes for denormalized data
    public static class CapabilityData {
        private Long id;
        private String name;
        private String description;
        private List<TechnologyData> technologies;

        public CapabilityData() {
        }

        public CapabilityData(Long id, String name, String description, List<TechnologyData> technologies) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.technologies = technologies;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<TechnologyData> getTechnologies() {
            return technologies;
        }

        public void setTechnologies(List<TechnologyData> technologies) {
            this.technologies = technologies;
        }
    }

    public static class TechnologyData {
        private Long id;
        private String name;
        private String description;

        public TechnologyData() {
        }

        public TechnologyData(Long id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class EnrollmentData {
        private Long personId;
        private String name;
        private String email;
        private LocalDateTime enrollmentDate;

        public EnrollmentData() {
        }

        public EnrollmentData(Long personId, String name, String email, LocalDateTime enrollmentDate) {
            this.personId = personId;
            this.name = name;
            this.email = email;
            this.enrollmentDate = enrollmentDate;
        }

        public Long getPersonId() {
            return personId;
        }

        public void setPersonId(Long personId) {
            this.personId = personId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalDateTime getEnrollmentDate() {
            return enrollmentDate;
        }

        public void setEnrollmentDate(LocalDateTime enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
        }
    }
}
