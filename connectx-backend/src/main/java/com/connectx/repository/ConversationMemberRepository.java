package com.connectx.repository;

import com.connectx.entity.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConversationMemberRepository
        extends JpaRepository<ConversationMember, UUID> {
}