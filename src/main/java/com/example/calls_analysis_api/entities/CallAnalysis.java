package com.example.calls_analysis_api.entities;

import tools.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
@Entity
@Table(name = "call_analyses")
@Data
public class CallAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_phone_number")
    private String clientPhoneNumber;
    @Column(name = "call_timestamp")
    private LocalDateTime callTimestamp;
    @Column(name = "audio_filename")
    private String audioFilename;
    @Column(name = "analysis_result", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode analysisResult;
}