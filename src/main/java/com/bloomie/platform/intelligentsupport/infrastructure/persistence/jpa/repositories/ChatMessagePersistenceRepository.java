package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.entities.ChatMessagePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessagePersistenceRepository
        extends JpaRepository<ChatMessagePersistenceEntity, Long> {

    List<ChatMessagePersistenceEntity> findAllBySupportQueryId(SupportQueryId supportQueryId);
}