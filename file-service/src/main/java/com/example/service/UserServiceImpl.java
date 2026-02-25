package com.example.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @DS("#org")
    @Override
    public User findByIdAndPassword(int id, String password, String org) {
        return userMapper.getUserByIdAndPassword(id, password);
    }
}
