package com.connectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "conversations")
public class ConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConversationType type;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected ConversationEntity() {

    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

}