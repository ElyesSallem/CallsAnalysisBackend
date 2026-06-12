package com.example.calls_analysis_api.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import tools.jackson.databind.JsonNode;
import lombok.Data;

@Data
@JsonFilter("CallResponseFilter")
public class CallResponse {

    // Database columns (metadata)
    private String client_phone_number;
    private String call_timestamp;
    private String treatment_timestamp;
    private String audio_filename;

    // JSON properties
    private String call_type;
    private JsonNode call_quality;
    private JsonNode analysis_steps;
    private JsonNode extracted_info;
    private JsonNode client_sentiment;
    private JsonNode call_type_justification;
}
