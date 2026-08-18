package com.github.sivaone.ai.mcp;

import lombok.extern.slf4j.Slf4j;

import io.modelcontextprotocol.client.McpClient;
import org.springframework.ai.mcp.customizer.McpClientCustomizer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomMcpSyncClientCustomizer implements McpClientCustomizer<McpClient.SyncSpec> {
    @Override
    public void customize(String name, McpClient.SyncSpec componentBuilder) {
        log.info("Customizing MCP client: {}", name);
    }
}
