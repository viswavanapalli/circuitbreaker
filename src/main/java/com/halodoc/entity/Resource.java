package com.halodoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Deque;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    private int id;
    private String resourceType;
    private String resourcePath;
    private boolean openStatus;
    private Map<Integer, Deque<Long>> timestamps;
    private long lastClosed;

    public Resource(String resourceType, String resourcePath, boolean openStatus, Map<Integer, Deque<Long>> timestamps) {
        this.resourceType = resourceType;
        this.resourcePath = resourcePath;
        this.openStatus = openStatus;
        this.timestamps = timestamps;
    }
}
