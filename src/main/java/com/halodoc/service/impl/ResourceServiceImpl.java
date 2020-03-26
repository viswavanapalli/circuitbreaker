package com.halodoc.service.impl;

import com.halodoc.entity.CircuitBreaker;
import com.halodoc.entity.Request;
import com.halodoc.entity.Resource;
import com.halodoc.repository.ResourceRepository;
import com.halodoc.service.ResourceService;

import java.util.Deque;
import java.util.LinkedList;

public class ResourceServiceImpl implements ResourceService {

    private ResourceRepository resourceRepository = ResourceRepository.getInstance();

    @Override
    public void serveRequest(Request request) {
        Resource resource = fetchResource(request.getRequestType(), request.getRequestPath());

        if(resource.getTimestamps().containsKey(request.getResponseCode()/100)){
            Deque<Long> d = resource.getTimestamps().get(request.getResponseCode()/100);
            //flushing timestamps older than t
            while(d.size()>0 && CircuitBreaker.t <= (request.getTimestamp() - d.getFirst())){
                d.removeFirst();
            }
            d.addLast(request.getTimestamp());

            if(request.getResponseCode()/100 ==5){
                if(d.size()> CircuitBreaker.n){
                    resource.setOpenStatus(false);
                    resource.setLastClosed(request.getTimestamp());
                    while(!d.isEmpty()){
                        d.removeFirst();
                    }
                    System.out.println("For request timestamp : " + request.getTimestamp() + " , response : circuit breaked set to closed state");
                } else {
                    System.out.println("For request timestamp : " + request.getTimestamp() + " , response : " + request.getResponseCode());
                }
            } else {
                System.out.println("For request timestamp : " + request.getTimestamp() + " , success : " + request.getResponseCode());
            }
        } else{
            Deque<Long> timestampDeQueue = new LinkedList<Long>();
            timestampDeQueue.addLast(request.getTimestamp()/100);
            resource.getTimestamps().put(request.getResponseCode()/100, timestampDeQueue);
            System.out.println("For request timestamp : " + request.getTimestamp() + " , response : " + request.getResponseCode());
        }
    }

    public Resource fetchResource (String requestType, String requestPath){
        Resource resource = resourceRepository.getResource(requestType, requestPath);
        return resource;
    }

    public void createResource(Resource resource){
        resourceRepository.saveResource(resource);
    }

}