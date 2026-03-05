package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    User getUserByIdAndPassword(Integer id, String password);

    List<User> getAllUsers();

    @Select("SELECT count(1) FROM t_user;")
    int getUserCount();
}
