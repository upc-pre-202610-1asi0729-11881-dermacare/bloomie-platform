package com.bloomie.platform.intelligentsupport.domain.model.commands;

// domain/model/commands/UpdateSupportQueryStatusCommand.java
public record UpdateSupportQueryStatusCommand(
        Long supportQueryId,
        String status
) {}