package com.bloomie.platform.intelligentsupport.domain.model.events;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;

/**
 * Domain event raised when a chat message has been sent.
 *
 * @param chatMessageId  the id of the sent message
 * @param supportQueryId the id of the parent support query
 * @param messageType    who authored the message (USER or AI)
 */
public record ChatMessageSentEvent(
        Long chatMessageId,
        Long supportQueryId,
        String messageType
) {
    public static ChatMessageSentEvent from(ChatMessage chatMessage) {
        return new ChatMessageSentEvent(
                chatMessage.getId(),
                chatMessage.getSupportQueryId(),
                chatMessage.getMessageType().name()
        );
    }
}