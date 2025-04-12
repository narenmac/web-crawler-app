package com.example.crawler;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class PageMeta {
    @Id
    @GeneratedValue
    private Long id;

    private String url;
    private String filename;
    private Instant timestamp;
}
