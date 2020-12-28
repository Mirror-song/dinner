package com.hqyj.dinner.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.dinner.entity.Tables;
import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.entity.Role;
import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.service.RoleService;
import com.hqyj.dinner.service.UserService;
import com.hqyj.dinner.util.MdFive;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Autowired
    MdFive mdFive;

    /**
     * 进入用户管理
     */
    @RequestMapping("/userPage")
    public String userPage() {
        return "sys_manage/user";
    }

    /**
     * 查询所有用户
     */
    @ResponseBody
    @RequestMapping("queryAllUser")
    public HashMap<String, Object> queryAllUser(User user) {
        HashMap<String, Object> map = new HashMap<>();
        //1 设置分页参数：页码和条数
        PageHelper.startPage(user.getPage(), user.getRows());
        //2 查询结果集合
        List<User> userList = userService.queryAllUser();
        // 3 创建分页对象
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        List<User> listResult = pageInfo.getList();
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("endPage", pageInfo.getNavigateLastPage());
        map.put("curPage", user.getPage());
        map.put("data", listResult);
        return map;
    }


    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, Object> add(User user) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        final List<User> userList = userService.list(wrapper);
        if (user.getUserName().isEmpty() || user.getPwd().isEmpty()) {
            map.put("info", "用户名和密码不能为空！");
        } else if (userList.isEmpty()) {
            user.setPwd(mdFive.encrypt(user.getPwd(), user.getUserName()));
            int result = userService.add(user);
            if (result > 0) {
                map.put("info", "新增成功");
            } else {
                map.put("info", "新增失败！");
            }
        } else {
            map.put("info", "用户已存在！");
        }
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public HashMap<String, Object> update(User user) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        User user_db = userService.getOne(wrapper);
        String userPwd = user.getPwd();
        if (user.getUserName().isEmpty() || user.getPwd().isEmpty()) {
            map.put("info", "用户名和密码不能为空！");
        } else if (user_db != null) {
            if (userPwd.equals(user_db.getPwd())) {
                int result = userService.modify(user);
                if (result > 0) {
                    map.put("info", "修改成功");
                } else {
                    map.put("info", "修改失败！");
                }
            } else {
                user.setPwd(mdFive.encrypt(userPwd, user.getUserName()));
                int result = userService.modify(user);
                if (result > 0) {
                    map.put("info", "修改成功");
                } else {
                    map.put("info", "修改失败！");
                }
            }
        }else {
            user.setPwd(mdFive.encrypt(userPwd, user.getUserName()));
            int result = userService.modify(user);
            if (result > 0) {
                map.put("info", "修改成功");
            } else {
                map.put("info", "修改失败！");
            }
        }
        return map;
    }

    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String, Object> del(User user) {
        HashMap<String, Object> map = new HashMap<>();
        int result = userService.delete(user);
        if (result > 0) {
            map.put("info", "删除成功");
        } else {
            map.put("info", "删除失败");
        }
        return map;
    }

    @RequestMapping("/select")
    @ResponseBody
    public HashMap<String, Object> select(User user) {
        HashMap<String, Object> map = new HashMap<>();
        if (user.getUserName().isEmpty() && user.getName().isEmpty() &&
                user.getStatus() == null && user.getRoleId() == null &&
                user.getTelNum().isEmpty()) {
            map.put("info", "请输入信息以查询");
        } else {
            //1 设置分页参数：页码和条数
            PageHelper.startPage(user.getPage(), user.getRows());
            //2 查询结果集合
            List<User> userList = userService.select(user);
            // 3 创建分页对象
            PageInfo<User> pageInfo = new PageInfo<>(userList);
            map.put("total", pageInfo.getTotal());
            map.put("pages", pageInfo.getPages());
            map.put("endPage", pageInfo.getNavigateLastPage());
            map.put("curPage", user.getPage());
            if (pageInfo.getList().isEmpty()) {
                map.put("info", "没有此条记录，请添加！");
            } else {
                map.put("data", pageInfo.getList());
                map.put("info", "查询成功");
            }
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/fpRole")
    public HashMap<String, Object> fpRole(User user) {
        HashMap<String, Object> map = new HashMap<>();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId());
        wrapper.set("role_id", user.getRoleId());
        if (userService.update(wrapper)) {
            map.put("info", "分配成功");
        } else {
            map.put("info", "分配失败");
        }
        return map;
    }

    @RequestMapping("/backUser")
    @ResponseBody
    public HashMap<String, Object> backUser(User user) {
        HashMap<String, Object> map = new HashMap<>();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId());
        wrapper.set("status", 1);
        if (userService.update(wrapper)) {
            map.put("info", "恢复成功");
        } else {
            map.put("info", "恢复失败！");
        }
        return map;
    }
}
