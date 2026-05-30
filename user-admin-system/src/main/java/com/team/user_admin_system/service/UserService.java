package com.team.user_admin_system.service;

import com.team.user_admin_system.entity.SysUser;
import com.team.user_admin_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 登录
    public SysUser login(String username) {
        return userRepository.findByUsername(username);
    }

    // 注册
    public SysUser register(SysUser user) {
        return userRepository.save(user);
    }

    // 查询全部用户
    public List<SysUser> getAll() {
        return userRepository.findAll();
    }

    // 根据ID查用户
    public SysUser getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}