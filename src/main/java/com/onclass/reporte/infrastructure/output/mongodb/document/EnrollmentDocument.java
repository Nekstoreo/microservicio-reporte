package com.onclass.reporte.infrastructure.output.mongodb.document;

import java.time.LocalDateTime;

public class EnrollmentDocument {
    private Long personId;
    private String name;
    private String email;
    private LocalDateTime enrollmentDate;

    public EnrollmentDocument() {
    }

    public EnrollmentDocument(Long personId, String name, String email, LocalDateTime enrollmentDate) {
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
