package com.example.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DS("#org")
    @Override
    public String findByIdAndPassword(int id, String password, String org) {
        String userName = jdbcTemplate.queryForObject("SELECT user_name FROM t_user where id=? and `password`=? limit 1",
                String.class, id, password);
        return userName;
    }
}
