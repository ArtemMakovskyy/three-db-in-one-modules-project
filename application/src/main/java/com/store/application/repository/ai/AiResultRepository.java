package com.store.application.repository.ai;

import com.store.application.model.ai.AiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiResultRepository extends JpaRepository<AiResult, Long> {
}
