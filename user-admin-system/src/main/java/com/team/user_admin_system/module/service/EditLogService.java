package com.team.user_admin_system.module.service;

import com.team.user_admin_system.module.entity.EditLog;
import java.util.List;

public interface EditLogService {
    EditLog saveLog(EditLog log);
    List<EditLog> getRecentLogs();
}
