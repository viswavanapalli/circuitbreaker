package com.halodoc.repository;

import com.halodoc.entity.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResourceRepository {
    private static int id=1;

    private volatile static ResourceRepository resourceRepository;

    private List<Resource> resourceDetails = new ArrayList<Resource>();

    private ResourceRepository(){};

    public static ResourceRepository getInstance()
    {
        if (resourceRepository == null)
        {
            // To make thread safe
            synchronized (ResourceRepository.class)
            {
                // check again as multiple threads
                // can reach above step
                if (resourceRepository==null)
                    resourceRepository = new ResourceRepository();
            }
        }
        return resourceRepository;
    }

    public Resource saveResource(Resource resource){
        resource.setId(id);
        resourceDetails.add(resource);
        id++;
        return resource;
    }

    public Resource getResource(String requestType, String requestPath){
        List<Resource> r = resourceDetails.stream()
                .filter(resource -> requestType.equals(resource.getResourceType()))
                .filter(resource -> requestPath.equals(resource.getResourcePath()))
                .collect(Collectors.toList());
        if(r.size()>0)
            return r.get(0);
        return null;
    }
}
