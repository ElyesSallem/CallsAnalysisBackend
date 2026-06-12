package com.example.calls_analysis_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class CallFilterRequest {
    private List<String> fields;
}
