package com.bloomie.platform.intelligentsupport.application.internal.queryservices;

import com.bloomie.platform.intelligentsupport.application.queryservices.ChatMessageQueryService;
import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetChatMessagesBySupportQueryIdQuery;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;
import com.bloomie.platform.intelligentsupport.domain.repositories.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that handles all read operations on the {@link ChatMessage} aggregate.
 *
 * <p>Delegates directly to the {@link ChatMessageRepository} domain port without any
 * mutation or event publication.</p>
 */
@Service
public class ChatMessageQueryServiceImpl implements ChatMessageQueryService {

    private final ChatMessageRepository chatMessageRepository;

    /**
     * Creates a new instance with the required repository dependency.
     *
     * @param chatMessageRepository the domain repository port for chat messages
     */
    public ChatMessageQueryServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ChatMessage> handle(GetChatMessagesBySupportQueryIdQuery query) {
        return chatMessageRepository.findAllBySupportQueryId(new SupportQueryId(query.supportQueryId()));
    }
}
