package com.halodoc;

import com.halodoc.controller.ResourceController;
import com.halodoc.entity.CircuitBreaker;
import com.halodoc.entity.Request;

public class CircuitBreakerApplication {

    public static void main(String[] args){
        CircuitBreaker.n = 2;
        CircuitBreaker.t = 5;
        CircuitBreaker.x = 3;
        String[] requests = {
                "0 GET /v1/products/{id} 200",
                "1 GET /v1/products/{id} 500",
                "2 GET /v1/products/{id} 502",
                "3 GET /v1/products/{id} 504",
                "4 GET /v1/products/{id} 504",
                "5 GET /v1/products/{id} 504",
                "6 GET /v1/products/{id} 504",
                "7 GET /v1/products/{id} 504",
                "8 GET /v1/products/{id} 504",
                "9 GET /v1/products/{id} 200"
        };

        ResourceController resourceController = new ResourceController();
        for (int i=0; i<requests.length; i++){
            String command = requests[i];

            String[] data = command.split(" ");
            Request request = Request
                    .builder()
                    .timestamp(Long.parseLong(data[0]))
                    .requestType(data[1])
                    .requestPath(data[2])
                    .responseCode(Integer.parseInt(data[3]))
                    .build();
            resourceController.serveReqeust(request);
        }
    }
}
