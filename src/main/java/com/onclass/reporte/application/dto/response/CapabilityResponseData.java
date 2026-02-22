package com.onclass.reporte.application.dto.response;

import java.util.List;

public class CapabilityResponseData {
    private Long id;
    private String name;
    private String description;
    private List<TechnologyResponseData> technologies;

    public CapabilityResponseData() {
    }

    public CapabilityResponseData(Long id, String name, String description, List<TechnologyResponseData> technologies) {
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

    public List<TechnologyResponseData> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyResponseData> technologies) {
        this.technologies = technologies;
    }
}
