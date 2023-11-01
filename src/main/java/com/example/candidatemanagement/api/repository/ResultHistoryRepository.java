package com.example.candidatemanagement.api.repository;

import com.example.candidatemanagement.api.model.ResultHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultHistoryRepository extends JpaRepository<ResultHistory, UUID>, JpaSpecificationExecutor<ResultHistory> {
}
