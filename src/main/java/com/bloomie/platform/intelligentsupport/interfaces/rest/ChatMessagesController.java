package com.bloomie.platform.intelligentsupport.interfaces.rest;

import com.bloomie.platform.intelligentsupport.application.commandservices.ChatMessageCommandService;
import com.bloomie.platform.intelligentsupport.application.queryservices.ChatMessageQueryService;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetChatMessagesBySupportQueryIdQuery;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.SendChatMessageResource;
import com.bloomie.platform.intelligentsupport.interfaces.rest.transform.ChatMessageResourceFromEntityAssembler;
import com.bloomie.platform.intelligentsupport.interfaces.rest.transform.SendChatMessageCommandFromResourceAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/chat-messages", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Chat Messages", description = "Intelligent Support — Chat Message Endpoints")
public class ChatMessagesController {

    private final ChatMessageCommandService commandService;
    private final ChatMessageQueryService queryService;

    public ChatMessagesController(ChatMessageCommandService commandService,
                                  ChatMessageQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Send a chat message and receive AI response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message sent and AI response generated."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Support query not found.")})
    public ResponseEntity<?> sendChatMessage(
            @Valid @RequestBody SendChatMessageResource resource) {
        var command = SendChatMessageCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ChatMessageResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @GetMapping("/support-query/{supportQueryId}")
    @Operation(summary = "Get all chat messages for a support query session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chat messages retrieved successfully.")})
    public ResponseEntity<?> getChatMessagesBySupportQueryId(
            @PathVariable Long supportQueryId) {
        var query = new GetChatMessagesBySupportQueryIdQuery(supportQueryId);
        var messages = queryService.handle(query);
        var resources = messages.stream()
                .map(ChatMessageResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}