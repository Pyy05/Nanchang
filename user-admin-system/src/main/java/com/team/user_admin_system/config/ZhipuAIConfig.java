package com.team.user_admin_system.config;

import com.zhipuai.core.ZhipuAI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZhipuAIConfig {
    
    @Value("${zhipuai.api-key}")
    private String apiKey;
    
    @Bean
    public ZhipuAI zhipuAI() {
        return new ZhipuAI(apiKey);
    }
}
