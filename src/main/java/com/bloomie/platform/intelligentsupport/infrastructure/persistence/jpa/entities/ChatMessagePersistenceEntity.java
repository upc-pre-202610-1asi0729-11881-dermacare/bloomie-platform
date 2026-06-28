package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.MessageType;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.converters.SupportQueryIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "chat_messages")
public class ChatMessagePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = SupportQueryIdPersistenceConverter.class)
    @Column(name = "support_query_id", nullable = false)
    private SupportQueryId supportQueryId;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    public ChatMessagePersistenceEntity() {}

}