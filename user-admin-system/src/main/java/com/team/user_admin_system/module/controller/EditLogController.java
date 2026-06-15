package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.EditLog;
import com.team.user_admin_system.module.service.EditLogService;
import com.team.user_admin_system.module.entity.EditLog;
import com.team.user_admin_system.module.repository.EditLogRepository;
import com.team.user_admin_system.module.service.EditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/edit-log")
public class EditLogController {

    @Autowired
    private EditLogService editLogService;

    @Autowired
    private EditLogRepository editLogRepository;

    @GetMapping("/clear")
    public String clearAll() {
        editLogRepository.deleteAll();
        return "cleared";
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getRecentLogs() {
        List<EditLog> logs = editLogService.getRecentLogs();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("msg", "success");
        result.put("data", logs);
        return ResponseEntity.ok(result);
    }
}
