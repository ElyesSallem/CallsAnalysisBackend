package com.example.calls_analysis_api.dto;

import lombok.Data;

@Data
public class AnalyzeCallRequest {
    private String audio_url;
    private String audio_filename;
    private String client_phone_number;
    private String call_timestamp;
}
