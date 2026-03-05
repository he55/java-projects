package com.example.service;

import com.example.pojo.User;

import java.util.List;

public interface UserService {
    User findByIdAndPassword(int id, String password, String org);
    List<User> findAll();
}
