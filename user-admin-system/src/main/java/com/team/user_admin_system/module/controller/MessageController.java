package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Message;
import com.team.user_admin_system.module.service.MessageService;
import com.team.user_admin_system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
//    留言提交
    @PostMapping("/submit")
    public Result<Message> submitMessage(@RequestBody Message message) {
        try {
            System.out.println("收到留言请求 - userId: " + message.getUserId() + ", content: " + message.getContent());
            
            if (message.getUserId() == null) {
                return Result.fail("用户未登录");
            }
            
            if (message.getContent() == null || message.getContent().trim().isEmpty()) {
                return Result.fail("问题内容不能为空");
            }
            
            Message savedMessage = messageService.submitMessage(message);
            return Result.success(savedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("留言提交失败：" + e.getMessage());
        }
    }
//    查询留言列表（全部留言、待回复留言、已回复留言）
    @GetMapping("/list")
    public Result<Map<String, Object>> getMessages(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            Page<Message> messagePage = messageService.getMessages(status, pageable);
            
            Map<String, Object> data = new HashMap<>();
            data.put("list", messagePage.getContent());
            data.put("total", messagePage.getTotalElements());
            data.put("pages", messagePage.getTotalPages());
            data.put("currentPage", page);
            
            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败：" + e.getMessage());
        }
    }
//    查询我的留言列表
    @GetMapping("/my")
    public Result<Map<String, Object>> getMyMessages(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            Page<Message> messagePage = messageService.getMyMessages(userId, pageable);
            
            Map<String, Object> data = new HashMap<>();
            data.put("list", messagePage.getContent());
            data.put("total", messagePage.getTotalElements());
            data.put("pages", messagePage.getTotalPages());
            data.put("currentPage", page);
            
            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败：" + e.getMessage());
        }
    }
//    回复留言
    @PostMapping("/reply/{messageId}")
    public Result<Message> replyMessage(
            @PathVariable Integer messageId,
            @RequestBody Map<String, String> request
    ) {
        try {
            String replyContent = request.get("replyContent");
            Message repliedMessage = messageService.replyMessage(messageId, replyContent);
            return Result.success(repliedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("回复失败：" + e.getMessage());
        }
    }

        /**
     * 删除留言接口
     * 根据留言ID删除指定的留言记录
     * 
     * @param messageId 留言ID，从URL路径中获取
     * @return Result<Void> 删除成功返回成功结果，失败返回包含错误信息的失败结果
     */
    //    删除留言
    @DeleteMapping("/delete/{messageId}")
    public Result<Void> deleteMessage(@PathVariable Integer messageId) {
        try {
            // 调用Service层执行删除操作
            messageService.deleteMessage(messageId);
            // 删除成功，返回成功响应
            return Result.success();
        } catch (Exception e) {
            // 打印异常堆栈信息，便于排查问题
            e.printStackTrace();
            // 删除失败，返回错误信息
            return Result.fail("删除失败：" + e.getMessage());
        }
    }

//    搜索留言
    @GetMapping("/search")
    public Result<Map<String, Object>> searchMessages(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            Page<Message> messagePage = messageService.searchMessages(keyword, pageable);
            
            Map<String, Object> data = new HashMap<>();
            data.put("list", messagePage.getContent());
            data.put("total", messagePage.getTotalElements());
            data.put("pages", messagePage.getTotalPages());
            data.put("currentPage", page);
            
            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败：" + e.getMessage());
        }
    }
}