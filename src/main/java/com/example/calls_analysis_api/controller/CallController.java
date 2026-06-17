package com.example.calls_analysis_api.controller;

import com.example.calls_analysis_api.dto.CallFilterRequest;
import com.example.calls_analysis_api.dto.CallResponse;
import com.example.calls_analysis_api.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/calls")
public class CallController {

    @Autowired
    private CallService callService;

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
}
