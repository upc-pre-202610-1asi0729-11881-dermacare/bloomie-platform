package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.entities.ChatMessagePersistenceEntity;

public final class ChatMessagePersistenceAssembler {

    private ChatMessagePersistenceAssembler() {}

    public static ChatMessage toDomainFromPersistence(ChatMessagePersistenceEntity entity) {
        return new ChatMessage(
                entity.getId(),
                entity.getSupportQueryId(),
                entity.getText(),
                entity.getMessageType(),
                entity.getSentAt()
        );
    }

    public static ChatMessagePersistenceEntity toPersistenceFromDomain(ChatMessage chatMessage) {
        var entity = new ChatMessagePersistenceEntity();
        entity.setId(chatMessage.getId());
        entity.setSupportQueryId(chatMessage.getSupportQueryIdValue());
        entity.setText(chatMessage.getText());
        entity.setMessageType(chatMessage.getMessageType());
        entity.setSentAt(chatMessage.getSentAt());
        return entity;
    }
}