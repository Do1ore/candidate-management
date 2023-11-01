package com.example.candidatemanagement.api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CollectionId;

import java.util.Date;

@Entity
public class ResultHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private Date date;
    @Column(name = "score")
    private Integer score;

    @ManyToOne
    private CandidateTest candidateTest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public CandidateTest getCandidateTest() {
        return candidateTest;
    }

    public void setCandidateTest(CandidateTest candidateTest) {
        this.candidateTest = candidateTest;
    }
}
