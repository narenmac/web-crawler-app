package com.example.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seeds")
public class SeedPublisherController {

    @Autowired
    private SeedPublisher seedPublisher;

    @PostMapping("/publish")
    public ResponseEntity<String> publishSeeds() {
        try {
            seedPublisher.publishSeeds();
            return ResponseEntity.ok("Seed URLs published to Kafka.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to publish seeds: " + e.getMessage());
        }
    }
}

