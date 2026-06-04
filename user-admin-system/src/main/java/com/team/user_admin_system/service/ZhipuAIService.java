package com.team.user_admin_system.service;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.service.model.ChatCompletionCreateParams;
import ai.z.openapi.service.model.ChatCompletionResponse;
import ai.z.openapi.service.model.ChatMessage;
import ai.z.openapi.service.model.ChatMessageRole;
import ai.z.openapi.service.model.ChatThinking;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class ZhipuAIService {
    private final ZhipuAiClient zhipuAiClient;

    public ZhipuAIService(ZhipuAiClient zhipuAiClient) {
        this.zhipuAiClient = zhipuAiClient;
    }

    public String chatWithAI(String userMessage, List<ChatMessage> history) {
        List<ChatMessage> messages = new ArrayList<>();

        //洪小星系统人设
        ChatMessage systemMsg = ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM.value())
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

        if (history != null && !history.isEmpty()) {
            messages.addAll(history);
        }

        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.USER.value())
                .content(userMessage)
                .build());

        //构建请求，glm-4.7-flash
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("glm-4.7-flash")
                .thinking(ChatThinking.builder().type("enabled").build())
                .messages(messages)
                .build();

        try {
            ChatCompletionResponse resp = zhipuAiClient.chat().createChatCompletion(params);
            // 转JSON解析，彻底避开实体类缺失、类型转换问题
            JSONObject json = JSON.parseObject(JSON.toJSONString(resp.getData()));
            JSONObject choice = json.getJSONArray("choices").getJSONObject(0);
            JSONObject msg = choice.getJSONObject("message");
            return msg.getString("content");
        } catch (Exception e) {
            e.printStackTrace();
            return "洪小星暂时掉线啦，稍后再试试~";
        }
    }
}