package com.example.candidatemanagement.api.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class ResultHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date")
    private Date date;
    @Column(name = "score")
    private Integer score;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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



}
