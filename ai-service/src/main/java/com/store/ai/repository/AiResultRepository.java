package com.store.ai.repository;

import com.store.ai.model.AiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiResultRepository extends JpaRepository<AiResult, Long> {
}
