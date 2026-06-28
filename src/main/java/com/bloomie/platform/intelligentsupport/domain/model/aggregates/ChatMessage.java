package com.bloomie.platform.intelligentsupport.domain.model.aggregates;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.*;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage extends AbstractDomainAggregateRoot<ChatMessage> {
    private Long id;
    private SupportQueryId supportQueryId;
    private MessageType messageType;
    private String text;
    private LocalDateTime sentAt;


    public ChatMessage(Long id, SupportQueryId supportQueryId, MessageType messageType, String text, LocalDateTime sentAt) {
        this.id = id;
        this.supportQueryId = supportQueryId;
        this.messageType = messageType;
        this.text = text;
        this.sentAt = sentAt;
    }
}
