package com.halodoc.controller;

import com.halodoc.entity.Request;
import com.halodoc.service.ResourceService;
import com.halodoc.service.impl.ProxyResourceServiceImpl;

public class ResourceController {

    public void serveReqeust(Request request){
        ResourceService resourceService = new ProxyResourceServiceImpl();
        resourceService.serveRequest(request);
    }
}
