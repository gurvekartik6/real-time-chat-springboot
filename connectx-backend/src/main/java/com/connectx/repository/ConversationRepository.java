package com.connectx.repository;

import com.connectx.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConversationRepository
        extends JpaRepository<ConversationEntity, UUID> {
}