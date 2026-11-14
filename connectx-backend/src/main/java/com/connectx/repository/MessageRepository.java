package com.connectx.repository;

import com.connectx.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface MessageRepository
        extends JpaRepository<Message, UUID> {

    Page<Message> findByConversation_Id(
            UUID conversationId,
            Pageable pageable
    );
}