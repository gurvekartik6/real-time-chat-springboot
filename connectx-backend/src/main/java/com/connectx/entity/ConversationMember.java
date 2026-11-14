package com.connectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "conversation_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_conversation_user",
                        columnNames = {"conversation_id", "user_id"}
                )
        }
)
@Getter
@Setter
public class ConversationMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    protected ConversationMember() {}

    @PrePersist
    protected void onCreate() {
        if (joinedAt == null) {
            joinedAt = Instant.now();
        }
    }

}