package com.example.candidatemanagement.api.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tests")
public class DirectionTest {


    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "test_direction",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "direction_id")
    )
    private List<Direction> applicableDirections;

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

    public List<Direction> getApplicableDirections() {
        return applicableDirections;
    }

    public void setApplicableDirections(List<Direction> applicableDirections) {
        this.applicableDirections = applicableDirections;
    }


}