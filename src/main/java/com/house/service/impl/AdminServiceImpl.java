package com.house.service.impl;

import java.util.List;

import com.house.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.mapper.AdminMapper;
import com.house.pojo.entity.Admin;
import com.house.pojo.entity.House;
import com.house.pojo.entity.Page;
import com.house.service.IAdminService;

/**
 * @author chriy
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper service;

    @Override
    public Admin adminAccess(Admin admin) {
        return service.adminAccess(admin);
    }

    @Override
    public List<User> findAllUser() {
        return service.findAllUser();
    }

    @Override
    public List<User> findSomeUser(Page page) {
        return service.findSomeUser(page);
    }

    @Override
    public List<House> findAllHouse() {
        return service.findAllHouse();
    }

    @Override
    public List<House> findSomeHouse(Page p) {
        return service.findSomeHouse(p);
    }

    @Override
    public int deleteHouse(int houseId) {
        return service.deleteHouse(houseId);
    }

    @Override
    public User findUserById(int userId) {
        return service.findUserById(userId);
    }

    @Override
    public int updateUser(User user) {
        return service.updateUser(user);
    }

    @Override
    public int deleteUser(int userId) {
        return service.deleteUser(userId);
    }

    @Override
    public Admin checkAdminPwd(Admin admin) {
        return service.checkAdminPwd(admin);
    }

    @Override
    public int changePassword(Admin admin) {
        return service.updateAdminPwd(admin);
    }
}
