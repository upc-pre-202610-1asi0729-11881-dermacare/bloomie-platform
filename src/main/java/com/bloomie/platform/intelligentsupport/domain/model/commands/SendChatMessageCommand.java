package com.bloomie.platform.intelligentsupport.domain.model.commands;

public record SendChatMessageCommand(
        Long supportQueryId,
        String text
) {}