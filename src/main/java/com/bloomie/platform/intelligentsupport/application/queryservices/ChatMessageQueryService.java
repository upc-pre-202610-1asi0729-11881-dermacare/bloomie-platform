package com.bloomie.platform.intelligentsupport.application.queryservices;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetChatMessagesBySupportQueryIdQuery;

import java.util.List;

/**
 * Application service interface for all read operations on the {@link ChatMessage} aggregate.
 */
public interface ChatMessageQueryService {

    /**
     * Returns all chat messages belonging to the given support query session.
     *
     * @param query the query carrying the support query id
     * @return a list of chat messages (may be empty)
     */
    List<ChatMessage> handle(GetChatMessagesBySupportQueryIdQuery query);
}
