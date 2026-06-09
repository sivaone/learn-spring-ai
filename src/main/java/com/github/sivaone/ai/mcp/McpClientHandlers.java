package com.github.sivaone.ai.mcp;

import io.modelcontextprotocol.client.McpAsyncClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.mcp.annotation.McpLogging;
import org.springframework.ai.mcp.annotation.McpToolListChanged;
import org.springframework.ai.mcp.annotation.spring.ClientMcpSyncHandlersRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class McpClientHandlers {

    @Autowired
    private ClientMcpSyncHandlersRegistry toolRegistry;

    /*@Autowired
    private List<McpSyncClient> mcpSyncClients;
    @Autowired
    private List<McpAsyncClient> mcpAsyncClients;

    @PostConstruct
    public void listMcpServers() {
        System.out.println("=== MCP Servers Configured ===");
        mcpSyncClients.forEach(client -> {
            System.out.println("Server: " + client.getServerInfo());
            System.out.println("Client: " + client.getClientInfo());
            System.out.println("------------------------------");
        });
    }*/

    @McpLogging(clients = "everything")
    public void handleLoggingMessage(McpSchema.LoggingMessageNotification notification) {
        System.out.println("Received log: " + notification.level() +
                " - " + notification.data());
    }

    @McpToolListChanged(clients = "everything")
    public void handleToolListChanged(List<McpSchema.Tool> updatedTools) {
        System.out.println("Tool list updated: " + updatedTools.size() + " tools available");
        // Update local tool registry
        toolRegistry.handleToolListChanged("everything", updatedTools);
    }
}
