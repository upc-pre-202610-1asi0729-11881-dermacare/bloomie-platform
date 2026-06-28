package com.bloomie.platform.intelligentsupport.application.internal.commandservices;

import com.bloomie.platform.intelligentsupport.application.commandservices.ChatMessageCommandService;
import com.bloomie.platform.intelligentsupport.application.internal.outboundservices.ai.AiService;
import com.bloomie.platform.intelligentsupport.domain.model.aggregates.ChatMessage;
import com.bloomie.platform.intelligentsupport.domain.model.commands.SendChatMessageCommand;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.MessageType;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;
import com.bloomie.platform.intelligentsupport.domain.repositories.ChatMessageRepository;
import com.bloomie.platform.intelligentsupport.domain.repositories.SupportQueryRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageCommandServiceImpl implements ChatMessageCommandService {

    private final ChatMessageRepository chatMessageRepository;
    private final SupportQueryRepository supportQueryRepository;
    private final AiService aiService;

    public ChatMessageCommandServiceImpl(ChatMessageRepository chatMessageRepository,
                                         SupportQueryRepository supportQueryRepository,
                                         AiService aiService) {
        this.chatMessageRepository = chatMessageRepository;
        this.supportQueryRepository = supportQueryRepository;
        this.aiService = aiService;
    }

    @Override
    public Result<ChatMessage, ApplicationError> handle(SendChatMessageCommand command) {
        try {
            // 1. Validate support query exists
            var supportQuery = supportQueryRepository.findById(command.supportQueryId());
            if (supportQuery.isEmpty()) {
                return Result.failure(ApplicationError.notFound(
                        "support-query", "intelligent.support.query.not.found"));
            }

            var userMessage = new ChatMessage(command);
            chatMessageRepository.save(userMessage);

            var query = supportQuery.get();
            var skinContext = "Skin Profile ID: " + query.getSkinProfileId()
                    + ", Patient ID: " + query.getPatientId();

            var aiText = aiService.generateSkincareResponse(command.text(), skinContext);

            var aiMessage = new ChatMessage(
                    new SupportQueryId(command.supportQueryId()),
                    aiText,
                    MessageType.AI
            );
            var savedAiMessage = chatMessageRepository.save(aiMessage);

            return Result.success(savedAiMessage);

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Chat Message", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Chat message sending", e.getMessage()));
        }
    }
}