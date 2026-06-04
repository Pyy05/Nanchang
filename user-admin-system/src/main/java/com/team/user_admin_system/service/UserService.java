package com.team.user_admin_system.service;

import com.team.user_admin_system.entity.SysUser;
import com.team.user_admin_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务逻辑层
 * 作用：处理用户相关的业务（登录、注册、查询等）
 * 介于 Controller 和 Repository 中间
 */
@Service // 标记这是业务类，让Spring管理
public class UserService {

    // 注入仓库类（操作数据库）
    private final UserRepository userRepository;

    // 构造方法注入（Spring自动传参）
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 登录业务：根据用户名查用户
     */
    public SysUser login(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 注册业务：保存用户到数据库
     * 密码已经在Controller加密过了
     */
    public SysUser register(SysUser user) {
        return userRepository.save(user);
    }

    /**
     * 查询所有用户
     */
    public List<SysUser> getAll() {
        return userRepository.findAll();
    }

    /**
     * 根据用户ID查询单个用户
     */
    public SysUser getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

        /**
     * 为指定用户增加积分
     * @param userId 用户ID
     * @param points 要增加的积分数值
     */
        public void addPoints(Integer userId, Integer points) {
            // 根据用户ID查询用户信息
            SysUser user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                // 如果用户积分字段为null，初始化为0，防止空指针异常
                if (user.getPoints() == null) {
                    user.setPoints(0);
                }
                // 计算新积分：原积分 + 新增积分
                user.setPoints(user.getPoints() + points);
                // 保存更新后的用户信息到数据库
                userRepository.save(user);
            }
        }

        /**
     * 获取积分排行榜
     * @param limit 要返回的用户数量限制
     * @return 按积分排序的用户列表（积分最高的在前）
     */
        public List<SysUser> getLeaderboard(int limit) {
            // 创建分页请求，从第0页开始，限制返回用户数量为limit
            return userRepository.findTopUsersByPoints(
                org.springframework.data.domain.PageRequest.of(0, limit)
            );
        }
}