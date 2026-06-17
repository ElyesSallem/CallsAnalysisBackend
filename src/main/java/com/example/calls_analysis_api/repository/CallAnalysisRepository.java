package com.example.calls_analysis_api.repository;

import com.example.calls_analysis_api.entities.CallAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CallAnalysisRepository extends JpaRepository<CallAnalysis, Long> {
    Optional<CallAnalysis> findFirstByAudioFilenameContaining(String uniqueId);
}

