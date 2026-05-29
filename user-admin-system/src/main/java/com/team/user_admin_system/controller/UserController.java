package com.team.user_admin_system.controller;

import com.team.user_admin_system.entity.SysUser;
import com.team.user_admin_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public Page<SysUser> list(@RequestParam int page, @RequestParam int size){
        return userService.findPage(page,size);
    }

    @PostMapping("/save")
    public SysUser save(@RequestBody SysUser user){
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id){
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public Optional<SysUser> getInfo(@PathVariable Integer id){
        return userService.getById(id);
    }
}