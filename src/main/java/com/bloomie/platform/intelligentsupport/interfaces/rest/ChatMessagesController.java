package com.bloomie.platform.intelligentsupport.interfaces.rest;

import com.bloomie.platform.intelligentsupport.application.commandservices.ChatMessageCommandService;
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

    public ChatMessagesController(ChatMessageCommandService commandService) {
        this.commandService = commandService;
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
}