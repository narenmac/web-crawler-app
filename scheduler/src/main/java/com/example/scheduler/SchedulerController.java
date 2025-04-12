package com.example.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class SchedulerController {
    @Autowired
    private SeedPublisher seedPublisher;

    @GetMapping("/publishSeeds")
    public String triggerSeedPublishing() {
        seedPublisher.publishSeeds();
        return "Seeds published!";
    }
}
