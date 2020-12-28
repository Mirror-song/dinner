package com.hqyj.dinner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hqyj.dinner.entity.Food;
import com.hqyj.dinner.entity.Tables;
import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.mapper.RoleMapper;
import com.hqyj.dinner.mapper.UserMapper;
import com.hqyj.dinner.mapper.UserRoleMapper;
import com.hqyj.dinner.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqyj.dinner.util.MdFive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User selectUserByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        return userMapper.selectOne(wrapper);
    }


    @Override
    public List<User> queryAllUser() {
        return userMapper.selectList(null);
    }
//增
    @Override
    public int add(User user) {
        return userMapper.insert(user);
    }
//改
    @Override
    public int modify(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",user.getId());
        return userMapper.update(user,queryWrapper);
    }
//删
    @Override
    public int delete(User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",user.getId());
        wrapper.set("status",0);
        return userMapper.update(user,wrapper);
    }
//查
    @Override
    public List<User> select(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!user.getUserName().isEmpty()) {
            queryWrapper.like("user_name", user.getUserName());
        }
        if (!user.getName().isEmpty()) {
            queryWrapper.like("name", user.getName());
        }
        if (user.getStatus() != null) {
            queryWrapper.eq("status", user.getStatus());
        }
        if (user.getRoleId() != null) {
            queryWrapper.eq("role_id", user.getRoleId());
        }
        if (!user.getTelNum().isEmpty()) {
            queryWrapper.like("tel_num", user.getTelNum());
        }
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public List<String> selectRole(String userName) {
        return userMapper.selectRole(userName);
    }
}
