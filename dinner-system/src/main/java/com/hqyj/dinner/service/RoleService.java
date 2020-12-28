package com.hqyj.dinner.service;

import com.hqyj.dinner.entity.Role;
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
public interface RoleService extends IService<Role> {
//加载所有角色信息
    List<Role> queryAllRole();

    int add(Role role);

    int delete(Role role);

    List<Role> select(Role role);

    int modify(Role role);
}
