package com.team.user_admin_system.controller;

import com.zhipuai.model.chat.Message;
import com.team.user_admin_system.service.ZhipuAIService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    
    private final ZhipuAIService zhipuAIService;
    
    public ChatController(ZhipuAIService zhipuAIService) {
        this.zhipuAIService = zhipuAIService;
    }
    
    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, Object> request) {
        String message = (String) request.get("message");
        @SuppressWarnings("unchecked")
        List<Message> history = (List<Message>) request.getOrDefault("history", List.of());
        
        String response = zhipuAIService.chatWithAI(message, history);
        
        return Map.of("response", response);
    }
}
