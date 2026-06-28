package com.bloomie.platform.intelligentsupport.domain.model.queries;

/**
 * Query to retrieve all chat messages belonging to a specific support query session.
 *
 * @param supportQueryId the id of the support query whose messages are requested
 */
public record GetChatMessagesBySupportQueryIdQuery(Long supportQueryId) {
}
