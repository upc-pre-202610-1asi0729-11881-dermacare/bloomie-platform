package com.bloomie.platform.intelligentsupport.interfaces.rest.transform;

import com.bloomie.platform.intelligentsupport.domain.model.commands.SendChatMessageCommand;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.SendChatMessageResource;

public final class SendChatMessageCommandFromResourceAssembler {

    private SendChatMessageCommandFromResourceAssembler() {}

    public static SendChatMessageCommand toCommandFromResource(SendChatMessageResource resource) {
        return new SendChatMessageCommand(
                resource.supportQueryId(),
                resource.text()
        );
    }
}