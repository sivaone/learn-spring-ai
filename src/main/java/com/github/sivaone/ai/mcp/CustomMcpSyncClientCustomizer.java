package com.github.sivaone.ai.mcp;

import io.modelcontextprotocol.client.McpClient;
import org.springframework.ai.mcp.customizer.McpClientCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomMcpSyncClientCustomizer implements McpClientCustomizer<McpClient.SyncSpec> {
    @Override
    public void customize(String name, McpClient.SyncSpec componentBuilder) {
        System.out.println("Customizing MCP client: " + name);
    }
}
