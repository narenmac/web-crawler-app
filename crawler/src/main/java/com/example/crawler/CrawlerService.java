package com.example.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Component
public class CrawlerService {
    private final PageMetaRepository repo;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CrawlerService(PageMetaRepository repo, KafkaTemplate<String, String> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "crawl-urls", groupId = "crawler")
    public void crawl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String filename = UUID.randomUUID() + ".html";
            Path path = Paths.get("html", filename);
            Files.createDirectories(path.getParent());
            Files.write(path, doc.outerHtml().getBytes(StandardCharsets.UTF_8));

            PageMeta meta = new PageMeta();
            meta.setUrl(url);
            meta.setFilename(filename);
            meta.setTimestamp(Instant.now());
            repo.save(meta);

            kafkaTemplate.send("parse-urls", filename);
        } catch (Exception e) {
            System.err.println("Failed to crawl: " + url + " - " + e.getMessage());
        }
    }
}

