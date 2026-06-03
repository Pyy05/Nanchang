package com.team.user_admin_system.controller;

import ai.z.openapi.service.model.ChatMessage;
import com.team.user_admin_system.service.ZhipuAIService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {
    private final ZhipuAIService zhipuAIService;
    public ChatController(ZhipuAIService zhipuAIService) {
        this.zhipuAIService = zhipuAIService;
    }

    @PostMapping
    public Map<String,String> chat(@RequestBody Map<String,Object> req){
        String msg = (String) req.get("message");
        @SuppressWarnings("unchecked")
        List<ChatMessage> history = (List<ChatMessage>) req.getOrDefault("history",List.of());
        String ans = zhipuAIService.chatWithAI(msg,history);
        return Map.of("response",ans);
    }
}