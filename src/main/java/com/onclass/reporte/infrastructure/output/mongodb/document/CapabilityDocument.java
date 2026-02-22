package com.onclass.reporte.infrastructure.output.mongodb.document;

import java.util.List;

public class CapabilityDocument {
    private Long id;
    private String name;
    private String description;
    private List<TechnologyDocument> technologies;

    public CapabilityDocument() {
    }

    public CapabilityDocument(Long id, String name, String description, List<TechnologyDocument> technologies) {
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

    public List<TechnologyDocument> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyDocument> technologies) {
        this.technologies = technologies;
    }
}
