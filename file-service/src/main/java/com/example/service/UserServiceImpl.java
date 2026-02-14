package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String findByIdAndPassword(int id, String password) {
        String userName = jdbcTemplate.queryForObject("SELECT user_name FROM t_user where id=? and `password`=? limit 1",
                String.class, id, password);
        return userName;
    }
}
