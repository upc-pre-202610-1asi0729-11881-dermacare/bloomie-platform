package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;
import com.bloomie.platform.intelligentsupport.domain.repositories.ChatMessageRepository;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.assemblers.ChatMessagePersistenceAssembler;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.repositories.ChatMessagePersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessagePersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ChatMessageRepositoryImpl(ChatMessagePersistenceRepository persistenceRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<ChatMessage> findAllBySupportQueryId(SupportQueryId supportQueryId) {
        return persistenceRepository.findAllBySupportQueryId(supportQueryId).stream()
                .map(ChatMessagePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        var savedEntity = persistenceRepository.save(
                ChatMessagePersistenceAssembler.toPersistenceFromDomain(chatMessage));
        return ChatMessagePersistenceAssembler.toDomainFromPersistence(savedEntity);
    }
}