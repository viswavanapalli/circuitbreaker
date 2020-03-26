package com.halodoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CircuitBreaker {
    public static int n;
    public static long t;
    public static long x;
}
