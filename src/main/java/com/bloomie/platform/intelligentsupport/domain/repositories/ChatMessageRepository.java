package com.bloomie.platform.intelligentsupport.domain.repositories;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;

import java.util.List;

public interface ChatMessageRepository {
    List<ChatMessage> findAllBySupportQueryId(SupportQueryId supportQueryId);
    ChatMessage save(ChatMessage chatMessage);
}
