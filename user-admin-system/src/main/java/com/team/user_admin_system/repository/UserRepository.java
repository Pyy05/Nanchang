package com.team.user_admin_system.repository;

import com.team.user_admin_system.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户仓库（DAO层）
 * 作用：直接操作数据库 user 表，负责增删改查
 * 继承 JpaRepository 后，不用写 SQL ！
 */
public interface UserRepository extends JpaRepository<SysUser, Integer> {

    /**
     * 根据 用户名 查询用户
     * Spring Data JPA 会自动根据方法名生成 SQL
     * 等价于：SELECT * FROM user WHERE username = ?
     */
    SysUser findByUsername(String username);

    @org.springframework.data.jpa.repository.Query("SELECT u FROM SysUser u ORDER BY u.points DESC")
    java.util.List<SysUser> findTopUsersByPoints(org.springframework.data.domain.Pageable pageable);
}