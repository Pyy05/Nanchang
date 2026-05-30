package com.team.user_admin_system.repository;

import com.team.user_admin_system.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SysUser, Integer> {
    SysUser findByUsername(String username);
}