package com.hqyj.dinner.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dinner.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
public interface UserService extends IService<User> {


    User selectUserByUserName(String userName);

    List<User> queryAllUser();

    int add(User user);

    int modify(User user);

    int delete(User user);

    List<User> select(User user);

    List<String> selectRole(String userName);
}
