package com.example.service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.example.dto.UserDto;
import com.example.dto.req.LoginDto;
import com.example.dto.resp.UserTokenDto;
import com.example.exception.BusinessException;
import com.example.pojo.User;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    public UserTokenDto login(LoginDto loginDto) {
        DynamicRoutingDataSource source = (DynamicRoutingDataSource) dataSource;
        Set<String> dsNames = source.getDataSources().keySet();
        if (!dsNames.contains(loginDto.getOrg())) {
            throw new BusinessException("机构编号无效");
        }

        User user = userService.findByIdAndPassword(loginDto.getUserId(), loginDto.getPassword(), loginDto.getOrg());
        if (user == null) {
            throw new BusinessException("用户名或者密码错误");
        }

        String token = jwtUtil.generateToken(new UserDto(loginDto.getOrg(), String.valueOf(loginDto.getUserId())));

        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setUserId(user.getUserId());
        userTokenDto.setUserName(user.getUserName());
        userTokenDto.setToken(token);
        return userTokenDto;
    }
}
