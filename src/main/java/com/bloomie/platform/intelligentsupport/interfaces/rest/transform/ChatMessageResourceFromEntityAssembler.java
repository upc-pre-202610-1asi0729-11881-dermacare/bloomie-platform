package com.bloomie.platform.intelligentsupport.interfaces.rest.transform;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.ChatMessageResource;

public final class ChatMessageResourceFromEntityAssembler {

    private ChatMessageResourceFromEntityAssembler() {}

    public static ChatMessageResource toResourceFromEntity(ChatMessage chatMessage) {
        return new ChatMessageResource(
                chatMessage.getId(),
                chatMessage.getSupportQueryId(),
                chatMessage.getText(),
                chatMessage.getMessageType().name(),
                chatMessage.getSentAt().toString()
        );
    }
}