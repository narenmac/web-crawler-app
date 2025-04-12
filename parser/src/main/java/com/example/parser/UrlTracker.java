package com.example.parser;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UrlTracker {
    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final AtomicInteger counter = new AtomicInteger(0);

    public boolean shouldAdd(String url) {
        return visited.add(url) && counter.get() < 1000 && counter.incrementAndGet() <= 1000;
    }
}

