package com.halodoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Request {
    private long timestamp;
    private String requestType;
    private String requestPath;
    private int responseCode;
}