package com.onclass.reporte.application.dto.request;

import java.util.List;

public class CapabilityRequestData {
    private Long id;
    private String name;
    private String description;
    private List<TechnologyRequestData> technologies;

    public CapabilityRequestData() {
    }

    public CapabilityRequestData(Long id, String name, String description, List<TechnologyRequestData> technologies) {
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

    public List<TechnologyRequestData> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyRequestData> technologies) {
        this.technologies = technologies;
    }
}
