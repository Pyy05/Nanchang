package com.team.user_admin_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 把所有 MyBatis 的东西全部删掉！只留这一个类！
@SpringBootApplication
public class UserAdminSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserAdminSystemApplication.class, args);
    }
}
