package com.house.service.impl;

import com.house.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.mapper.UserMapper;
import com.house.service.IUserService;

/**
 * @author chriy
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public User login(User user) {
        return mapper.login(user);
    }

    @Override
    public int register(User user) {
        return mapper.register(user);
    }

    @Override
    public int updateUserPwd(User user) {
        return mapper.updateUserPwd(user);
    }

    @Override
    public User checkOldPwd(User user) {
        return mapper.checkOldPwd(user);
    }

    @Override
    public int updateBalance(User user) {
        return mapper.updateBalance(user);
    }

    @Override
    public User findUserByPublisher(String publisher) {
        return mapper.findUserByPublisher(publisher);
    }
}
