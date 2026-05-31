package com.team.user_admin_system.controller;

import com.team.user_admin_system.entity.SysUser;
import com.team.user_admin_system.service.UserService;
import com.team.user_admin_system.util.Result;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    // 完整构造函数（必须写在类里，不能在方法外面）
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. 登录接口
    @PostMapping("/login")
    public Result<SysUser> login(@RequestBody SysUser user) {
        SysUser loginUser = userService.login(user.getUsername());

        if (loginUser == null) {
            return Result.fail("用户不存在");
        }
        // 加密比对
        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            return Result.fail("密码错误");
        }

        return Result.success(loginUser);
    }

    // 2. 注册接口（带密码加密）
    @PostMapping("/register")
    public Result<?> register(@RequestBody SysUser user) {
        SysUser exist = userService.login(user.getUsername());
        if (exist != null) {
            return Result.fail("用户名已存在");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.register(user);
        return Result.success();
    }

    // 3. 查询所有用户
    @GetMapping("/list")
    public Result<List<SysUser>> list() {
        List<SysUser> list = userService.getAll();
        return Result.success(list);
    }

    // 4. 根据ID查询
    @GetMapping("/get/{id}")
    public Result<SysUser> get(@PathVariable Integer id) {
        SysUser user = userService.getById(id);
        return Result.success(user);
    }
}