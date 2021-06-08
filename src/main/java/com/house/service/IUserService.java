package com.house.service;

import com.house.pojo.entity.User;

/**
 * @author chriy
 */
public interface IUserService {
    /**
     * 用户登录
     *
     * @param user user
     * @return user
     */
    User login(User user);

    /**
     * 用户注册
     *
     * @param user user
     * @return user
     */
    int register(User user);

    /**
     * 修改密码
     *
     * @param user user
     * @return user
     */
    int updateUserPwd(User user);

    /**
     * 检查旧密码
     *
     * @param user user
     * @return user
     */
    User checkOldPwd(User user);

    /**
     * 更新金额
     *
     * @param user
     * @return
     */
    int updateBalance(User user);

    /**
     * 通过昵称找用户
     *
     * @param publisher
     * @return
     */
    User findUserByPublisher(String publisher);
}
