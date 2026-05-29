package com.team.user_admin_system.service;

import com.team.user_admin_system.entity.SysUser;
import com.team.user_admin_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<SysUser> findPage(int page, int size){
        return userRepository.findAll(PageRequest.of(page-1, size));
    }

    public SysUser save(SysUser user){
        return userRepository.save(user);
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    public Optional<SysUser> getById(Integer id){
        return userRepository.findById(id);
    }
}