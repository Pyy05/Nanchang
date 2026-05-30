package com.team.user_admin_system.controller;

import com.team.user_admin_system.entity.SysUser;
import com.team.user_admin_system.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. 登录接口
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody SysUser user) {
        Map<String, Object> map = new HashMap<>();
        SysUser loginUser = userService.login(user.getUsername());

        if (loginUser == null) {
            map.put("code", 0);
            map.put("msg", "用户不存在");
            return map;
        }
        if (!loginUser.getPassword().equals(user.getPassword())) {
            map.put("code", 0);
            map.put("msg", "密码错误");
            return map;
        }

        map.put("code", 1);
        map.put("msg", "登录成功");
        map.put("user", loginUser);
        return map;
    }

    // 2. 注册接口
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody SysUser user) {
        Map<String, Object> map = new HashMap<>();
        SysUser exist = userService.login(user.getUsername());

        if (exist != null) {
            map.put("code", 0);
            map.put("msg", "用户名已存在");
            return map;
        }

        userService.register(user);
        map.put("code", 1);
        map.put("msg", "注册成功");
        return map;
    }

    // 3. 查询所有用户
    @GetMapping("/list")
    public List<SysUser> list() {
        return userService.getAll();
    }

    // 4. 根据ID查询
    @GetMapping("/get/{id}")
    public SysUser get(@PathVariable Integer id) {
        return userService.getById(id);
    }
}