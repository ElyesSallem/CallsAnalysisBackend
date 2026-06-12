package com.example.calls_analysis_api.controller;

import com.example.calls_analysis_api.dto.CallResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tools.jackson.databind.ser.FilterProvider;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class CallResponseFilterAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return CallResponse.class.isAssignableFrom(returnType.getParameterType()) &&
               JacksonJsonHttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }

    @Override
    public Map<String, Object> determineWriteHints(Object body, MethodParameter returnType, MediaType contentType,
                                                   Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest servletRequest = getCurrentServletRequest();
        if (servletRequest == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Set<String> fields = (Set<String>) servletRequest.getAttribute("filterFields");

        Map<String, Object> hints = new HashMap<>();
        if (fields != null && !fields.isEmpty()) {
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter(
                    "CallResponseFilter",
                    SimpleBeanPropertyFilter.filterOutAllExcept(fields)
            );
            hints.put("tools.jackson.databind.ser.FilterProvider", filterProvider);
        } else {
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter(
                    "CallResponseFilter",
                    SimpleBeanPropertyFilter.serializeAll()
            );
            hints.put("tools.jackson.databind.ser.FilterProvider", filterProvider);
        }
        return hints;
    }

    private HttpServletRequest getCurrentServletRequest() {
        try {
            org.springframework.web.context.request.RequestAttributes attribs =
                    org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
            if (attribs instanceof org.springframework.web.context.request.ServletRequestAttributes) {
                return ((org.springframework.web.context.request.ServletRequestAttributes) attribs).getRequest();
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }
}
