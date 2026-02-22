package com.onclass.reporte.infrastructure.entities;

import java.util.List;

public class CapabilityEntity {
    private Long id;
    private String name;
    private String description;
    private List<TechnologyEntity> technologies;

    public CapabilityEntity() {
    }

    public CapabilityEntity(Long id, String name, String description, List<TechnologyEntity> technologies) {
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

    public List<TechnologyEntity> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyEntity> technologies) {
        this.technologies = technologies;
    }
}
