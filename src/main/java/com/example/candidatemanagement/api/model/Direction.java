package com.example.candidatemanagement.api.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "directions")
public class Direction {

    @Id
    @GeneratedValue
    private UUID id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value")
    private Long value;
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}