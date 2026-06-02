package com.team.user_admin_system.service;

import com.zhipuai.core.ZhipuAI;
import com.zhipuai.model.chat.ChatCompletionRequest;
import com.zhipuai.model.chat.ChatCompletionResponse;
import com.zhipuai.model.chat.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZhipuAIService {
    
    private final ZhipuAI zhipuAI;
    
    public ZhipuAIService(ZhipuAI zhipuAI) {
        this.zhipuAI = zhipuAI;
    }
    
    public String chatWithAI(String userMessage, List<Message> history) {
        List<Message> messages = new ArrayList<>();
        
        // 系统提示词 - 洪小星人设
        Message systemMsg = Message.builder()
                .role("system")
                .content("""
                    你是洪小星，南昌历史小管家。
                    任务：
                    1. 只回答南昌历史、八一南昌起义、红色文化、英雄城故事。
                    2. 回答简洁、口语化、可爱。
                    3. 不知道就说"这个我还没学到哦～"
                    4. 不回答无关内容（游戏、娱乐、八卦、暴力、色情等）。
                    """)
                .build();
        messages.add(systemMsg);
        
        // 添加历史对话
        if (history != null) {
            messages.addAll(history);
        }
        
        // 添加用户消息
        messages.add(Message.builder()
                .role("user")
                .content(userMessage)
                .build());
        
        // 调用API
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("glm-4.7-flash")
                .messages(messages)
                .build();
        
        ChatCompletionResponse response = zhipuAI.chat.completions.create(request);
        
        return response.getChoices().get(0).getMessage().getContent();
    }
}
