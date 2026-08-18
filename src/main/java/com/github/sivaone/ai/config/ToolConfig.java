package com.github.sivaone.ai.config;

import com.github.sivaone.ai.tools.DateTimeTools;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    @Bean
    public MethodToolCallbackProvider methodToolCallbackProvider(DateTimeTools dateTimeTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(dateTimeTools)
                .build();
    }
}
