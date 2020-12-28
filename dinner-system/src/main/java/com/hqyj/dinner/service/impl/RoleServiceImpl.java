package com.hqyj.dinner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hqyj.dinner.entity.Role;
import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.mapper.RoleMapper;
import com.hqyj.dinner.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> queryAllRole() {
        return roleMapper.selectList(null);
    }

    @Override
    public int add(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int delete(Role role) {
        UpdateWrapper<Role> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",role.getId());
        wrapper.set("status",0);
        return roleMapper.update(role,wrapper);
    }

    @Override
    public List<Role> select(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (role.getId()!=null){
            queryWrapper.eq("id",role.getId());
        }
        if (!role.getRoleName().isEmpty()){
            queryWrapper.like("role_name",role.getRoleName());
        }
        if (role.getStatus()!=null){
            queryWrapper.eq("status",role.getStatus());
        }
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public int modify(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",role.getId());
        return roleMapper.update(role,queryWrapper);
    }
}
