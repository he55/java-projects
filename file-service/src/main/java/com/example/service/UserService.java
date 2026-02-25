package com.example.service;

import com.example.pojo.User;

public interface UserService {
    User findByIdAndPassword(int id, String password, String org);
}
