package com.example.candidatemanagement.api.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class CandidateDirectionTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Candidate candidate;

    @ManyToOne
    private DirectionTest test;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultHistory> resultHistories;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public DirectionTest getTest() {
        return test;
    }

    public void setTest(DirectionTest test) {
        this.test = test;
    }

    public List<ResultHistory> getResultHistories() {
        return resultHistories;
    }

    public void setResultHistories(List<ResultHistory> resultHistories) {
        this.resultHistories = resultHistories;
    }
}

