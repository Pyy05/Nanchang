package com.team.user_admin_system.module.service;

import com.team.user_admin_system.module.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    Message submitMessage(Message message);

    Page<Message> getMessages(Integer status, Pageable pageable);

    Page<Message> getMyMessages(Integer userId, Pageable pageable);

    Message replyMessage(Integer messageId, String replyContent);
}