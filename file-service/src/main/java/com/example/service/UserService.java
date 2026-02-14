package com.example.service;

public interface UserService {
    String findByIdAndPassword(int id, String password, String org);
}
