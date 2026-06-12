package com.example.calls_analysis_api.service;

import com.example.calls_analysis_api.dto.CallResponse;
import com.example.calls_analysis_api.repository.CallAnalysisRepository;
import com.example.calls_analysis_api.entities.CallAnalysis;
import tools.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class CallService {

    @Autowired
    private CallAnalysisRepository callAnalysisRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public CallResponse getCallResponseById(Long id) {
        CallAnalysis call = callAnalysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Call analysis not found for ID: " + id));

        CallResponse response = new CallResponse();
        
        // 1. Populate metadata fields from database columns
        response.setAudio_filename(call.getAudioFilename());
        response.setClient_phone_number(call.getClientPhoneNumber());
        if (call.getCallTimestamp() != null) {
            response.setCall_timestamp(call.getCallTimestamp().format(FORMATTER));
        }

        // 2. Direct extraction from the raw JsonNode
        JsonNode json = call.getAnalysisResult();
        if (json != null) {
            response.setCall_type(json.path("call_type").asText(null));
            response.setTreatment_timestamp(json.path("treatment_timestamp").asText(null));
            
            // Map nested sub-objects directly
            response.setCall_quality(json.path("call_quality"));
            response.setAnalysis_steps(json.path("analysis_steps"));
            response.setExtracted_info(json.path("extracted_info"));
            response.setClient_sentiment(json.path("client_sentiment"));
            response.setCall_type_justification(json.path("call_type_justification"));
        }

        return response;
    }
}
