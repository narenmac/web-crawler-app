package com.example.scheduler;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeedPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishSeeds() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seed_urls.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String url;
            while ((url = reader.readLine()) != null) {
                kafkaTemplate.send("crawl-urls", url);
                System.out.println("Published: " + url);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to publish seed URLs", e);
        }
    }
}



