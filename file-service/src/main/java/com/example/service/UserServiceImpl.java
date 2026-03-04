package com.example.service;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByIdAndPassword(int id, String password, String org) {
        try {
            DynamicDataSourceContextHolder.push(org);
            return userMapper.getUserByIdAndPassword(id, password);
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    @Override
    public List<User> findAll() {
        return userMapper.getAllUsers();
    }
}
