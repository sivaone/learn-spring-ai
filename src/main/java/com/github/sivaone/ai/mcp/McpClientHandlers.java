package com.github.sivaone.ai.mcp;

import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.mcp.annotation.McpLogging;
import org.springframework.ai.mcp.annotation.McpToolListChanged;
import org.springframework.ai.mcp.annotation.spring.ClientMcpSyncHandlersRegistry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class McpClientHandlers {

    private final ClientMcpSyncHandlersRegistry toolRegistry;

    public McpClientHandlers(ClientMcpSyncHandlersRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    @McpLogging(clients = "everything")
    public void handleLoggingMessage(McpSchema.LoggingMessageNotification notification) {
        log.info("Received log: {} - {}", notification.level(), notification.data());
    }

    @McpToolListChanged(clients = "everything")
    public void handleToolListChanged(List<McpSchema.Tool> updatedTools) {
        log.info("Tool list updated: {} tools available", updatedTools.size());
        // Update local tool registry
        toolRegistry.handleToolListChanged("everything", updatedTools);
    }
}
