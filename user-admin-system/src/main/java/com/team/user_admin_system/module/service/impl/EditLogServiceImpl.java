package com.team.user_admin_system.module.service.impl;

import com.team.user_admin_system.module.entity.EditLog;
import com.team.user_admin_system.module.repository.EditLogRepository;
import com.team.user_admin_system.module.service.EditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditLogServiceImpl implements EditLogService {

    @Autowired
    private EditLogRepository editLogRepository;

    @Override
    public EditLog saveLog(EditLog log) {
        return editLogRepository.save(log);
    }

    @Override
    public List<EditLog> getRecentLogs() {
        return editLogRepository.findTop10ByOrderByCreateTimeDesc();
    }
}
