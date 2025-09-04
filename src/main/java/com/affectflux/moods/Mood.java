package com.affectflux.moods;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
@Table(name = "mood", indexes = @Index(name = "idx_mood_created_at", columnList = "created_at"))
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private int intensity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Mood() {
    }

    public Mood(String description, int intensity) {
        this.description = description;
        this.intensity = intensity;
        this.createdAt = Instant.now();
    }

    public Mood(String description, int intensity, Instant createdAt) {
        this.description = description;
        this.intensity = intensity;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getIntensity() {
        return intensity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}