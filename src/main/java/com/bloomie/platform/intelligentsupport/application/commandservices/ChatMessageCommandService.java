package com.bloomie.platform.intelligentsupport.application.commandservices;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.domain.model.commands.SendChatMessageCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

public interface ChatMessageCommandService {
    Result<ChatMessage, ApplicationError> handle(SendChatMessageCommand command);
}