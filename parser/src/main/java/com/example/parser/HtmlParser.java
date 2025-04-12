package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.jsoup.nodes.Element;


@Component
public class HtmlParser {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UrlTracker urlTracker;

    public HtmlParser(KafkaTemplate<String, String> kafkaTemplate, UrlTracker urlTracker) {
        this.kafkaTemplate = kafkaTemplate;
        this.urlTracker = urlTracker;
    }

    @KafkaListener(topics = "parse-urls", groupId = "parser")
    public void parse(String filename) {
        try {
            Path path = Paths.get("/app/html/", filename);
            Document doc = Jsoup.parse(path.toFile(), "UTF-8");

            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String absUrl = link.absUrl("href");
                if (urlTracker.shouldAdd(absUrl)) {
                    System.out.println("Sent metadata for file: " + filename);
                    kafkaTemplate.send("crawl-urls", absUrl);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse file: " + filename + " - " + e.getMessage());
        }
    }
}

