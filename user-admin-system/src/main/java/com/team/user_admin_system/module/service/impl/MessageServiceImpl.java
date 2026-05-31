package com.team.user_admin_system.module.service.impl;

import com.team.user_admin_system.module.entity.Message;
import com.team.user_admin_system.module.repository.MessageRepository;
import com.team.user_admin_system.module.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message submitMessage(Message message) {
        message.setStatus(0);
        message.setCreateTime(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> getMessages(Integer status, Pageable pageable) {
        if (status == null) {
            return messageRepository.findAllByOrderByCreateTimeDesc(pageable);
        }
        return messageRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<Message> getMyMessages(Integer userId, Pageable pageable) {
        return messageRepository.findByUserIdOrderByCreateTimeDesc(userId, pageable);
    }

    @Override
    public Message replyMessage(Integer messageId, String replyContent) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("留言不存在"));
        
        message.setReply(replyContent);
        message.setStatus(1);
        
        return messageRepository.save(message);
    }
}