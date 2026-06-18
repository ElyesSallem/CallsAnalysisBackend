package com.example.calls_analysis_api.controller;

import com.example.calls_analysis_api.dto.AnalyzeCallRequest;
import com.example.calls_analysis_api.dto.CallFilterRequest;
import com.example.calls_analysis_api.dto.CallResponse;
import com.example.calls_analysis_api.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/calls")
public class CallController {

    @Autowired
    private CallService callService;

    @Value("${n8n.webhook.url:http://localhost:5678/webhook/analyze-call}")
    private String n8nWebhookUrl;

    // Handles POST /api/calls/{idOrUniqueId} with list of required fields inside request body
    @PostMapping("/{idOrUniqueId}")
    public CallResponse getCallDetails(
            @PathVariable String idOrUniqueId,
            @RequestBody(required = false) CallFilterRequest request,
            HttpServletRequest servletRequest) {

        CallResponse response = callService.getCallResponseById(idOrUniqueId);
        List<String> fields = (request != null) ? request.getFields() : null;

        if (fields != null && !fields.isEmpty()) {
            servletRequest.setAttribute("filterFields", new HashSet<>(fields));
        }

        return response;
    }

    // Handles POST /api/calls/analyze to trigger n8n analysis workflow
    @PostMapping("/analyze")
    public CallResponse analyzeCall(@RequestBody AnalyzeCallRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(n8nWebhookUrl, request, CallResponse.class);
    }
}
