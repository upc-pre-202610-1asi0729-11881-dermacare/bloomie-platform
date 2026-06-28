package com.bloomie.platform.intelligentsupport.domain.model.aggregates;

import com.bloomie.platform.intelligentsupport.domain.model.commands.SendChatMessageCommand;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.MessageType;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ChatMessage extends AbstractDomainAggregateRoot<ChatMessage> {

    @Getter @Setter
    private Long id;

    private SupportQueryId supportQueryId;

    @Getter
    private String text;

    @Getter
    private MessageType messageType;

    @Getter
    private LocalDateTime sentAt;

    public ChatMessage(Long id, SupportQueryId supportQueryId, String text,
                       MessageType messageType, LocalDateTime sentAt) {
        this.id = id;
        this.supportQueryId = supportQueryId;
        this.text = text;
        this.messageType = messageType;
        this.sentAt = sentAt;
    }

    public ChatMessage(SupportQueryId supportQueryId, String text, MessageType messageType) {
        this(null, supportQueryId, text, messageType, LocalDateTime.now());
    }

    public ChatMessage(SendChatMessageCommand command) {
        this(new SupportQueryId(command.supportQueryId()), command.text(), MessageType.USER);
    }

    public Long getSupportQueryId() { return supportQueryId.supportQueryId(); }
    public SupportQueryId getSupportQueryIdValue() { return supportQueryId; }
}