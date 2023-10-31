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

}