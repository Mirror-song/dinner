package com.hqyj.dinner.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.dinner.entity.FoodType;
import com.hqyj.dinner.entity.Role;
import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.service.RoleService;
import com.hqyj.dinner.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;


    /**
     * 进入用户管理
     */
    @RequestMapping("/rolePage")
    public String rolePage() {
        return "sys_manage/role";
    }

    /**
     * 查询所有用户
     */
    @ResponseBody
    @RequestMapping("queryAllRole")
    public HashMap<String, Object> queryAllRole(Role role) {
        HashMap<String, Object> map = new HashMap<>();
        //1 设置分页参数：页码和条数
        PageHelper.startPage(role.getPage(), role.getRows());
        //2 查询结果集合
        List<Role> roleList = roleService.queryAllRole();
        // 3 创建分页对象
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        List<Role> listResult = pageInfo.getList();
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("endPage", pageInfo.getNavigateLastPage());
        map.put("curPage", role.getPage());
        map.put("data", listResult);
        return map;
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public HashMap<String, Object> findAll() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<Role> roleList = roleService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", roleList);
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, Object> add(Role role) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", role.getRoleName());
        final Role role_db = roleService.getOne(wrapper);
        if (role.getRoleName().isEmpty()) {
            map.put("info", "角色名不能为空！");
        } else if (role_db == null) {
            int result = roleService.add(role);
            if (result > 0) {
                map.put("info", "新增成功");
            } else {
                map.put("info", "新增失败！");
            }
        } else {
            map.put("info", "角色已存在！");
        }
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public HashMap<String, Object> update(Role role) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", role.getRoleName());
        final Role role_db = roleService.getOne(wrapper);
        if (role.getRoleName().isEmpty()) {
            map.put("info", "角色名不能为空！");
        } else if (role_db == null) {
            int result = roleService.modify(role);
            if (result > 0) {
                map.put("info", "修改成功");
            } else {
                map.put("info", "修改失败！");
            }
        } else {
            map.put("info", "角色已存在！");
        }
        return map;
    }

    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String, Object> del(Role role) {
        HashMap<String, Object> map = new HashMap<>();
        int result = roleService.delete(role);
        if (result > 0) {
            map.put("info", "删除成功");
        } else {
            map.put("info", "删除失败");
        }
        return map;
    }

    @RequestMapping("/select")
    @ResponseBody
    public HashMap<String, Object> select(Role role) {
        HashMap<String, Object> map = new HashMap<>();
        if (role.getRoleName().isEmpty() && role.getId() == null && role.getStatus() == null) {
            map.put("info", "请输入信息以查询");
        } else {
            //1 设置分页参数：页码和条数
            PageHelper.startPage(role.getPage(), role.getRows());
            //2 查询结果集合
            List<Role> roleList = roleService.select(role);
            // 3 创建分页对象
            PageInfo<Role> pageInfo = new PageInfo<>(roleList);
            map.put("data", pageInfo.getList());
            List<Role> listResult = pageInfo.getList();
            if (listResult.isEmpty()) {
                map.put("info", "没有此记录，请添加");
            } else {
                map.put("total", pageInfo.getTotal());
                map.put("pages", pageInfo.getPages());
                map.put("endPage", pageInfo.getNavigateLastPage());
                map.put("curPage", role.getPage());
                map.put("data", pageInfo.getList());
                map.put("info", "查询成功");
            }
        }
        return map;
    }

    @RequestMapping("/backRole")
    @ResponseBody
    public HashMap<String, Object> backUser(Role role) {
        HashMap<String, Object> map = new HashMap<>();
        UpdateWrapper<Role> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", role.getId());
        wrapper.set("status", 1);
        if (roleService.update(wrapper)) {
            map.put("info", "恢复成功");
        } else {
            map.put("info", "恢复失败！");
        }
        return map;
    }
}
