package com.halodoc.service.impl;

import com.halodoc.entity.CircuitBreaker;
import com.halodoc.entity.Request;
import com.halodoc.entity.Resource;
import com.halodoc.service.ResourceService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Deque;
import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProxyResourceServiceImpl implements ResourceService {
    private ResourceServiceImpl resourceServiceImpl = new ResourceServiceImpl();

    @Override
    public void serveRequest(Request request) {
        //check whether circuit for resource is open
        Resource resource = resourceServiceImpl.fetchResource(request.getRequestType(), request.getRequestPath());
        if(resource == null){
            resourceServiceImpl.createResource(
                    new Resource(request.getRequestType(),
                            request.getRequestPath(),
                            true ,
                            new HashMap<Integer, Deque<Long>>()));
            resourceServiceImpl.serveRequest(request);
        } else {
            if (resource.isOpenStatus()) {
                resourceServiceImpl.serveRequest(request);
            } else {
                if (request.getTimestamp() - resource.getLastClosed() > CircuitBreaker.x) {
                    resource.setOpenStatus(true);
                    resourceServiceImpl.serveRequest(request);
                    System.out.println("For request timestamp : " + request.getTimestamp() + " , response : circuit breaker set to open state");
                } else {
                    System.out.println("For request timestamp : " + request.getTimestamp() + " , response : circuit breaked as already in closed state");
                }
            }
        }
    }
}
