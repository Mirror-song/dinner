package com.hqyj.dinner.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.dinner.entity.Tables;
import com.hqyj.dinner.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Luo
 * @since 2020-10-27
 */
@Controller
@RequestMapping("table")
public class TablesController {
    @Autowired
    TablesService tablesService;

    @RequestMapping("tablePage")
    public String tablePage() {
        return "table";
    }

    //查询没有被占用的餐桌
    @ResponseBody
    @RequestMapping("queryTable")
    public HashMap<String, Object> queryTable() {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Tables> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        final List<Tables> tableList = tablesService.list(wrapper);
        map.put("data", tableList);
        return map;
    }

    //查询所有餐桌（包括被占用的）
    @ResponseBody
    @RequestMapping("queryAllTable")
    public HashMap<String, Object> queryAllTable(Tables tables) {
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(tables.getPage(), tables.getRows());
        final List<Tables> tablesList = tablesService.list();
        PageInfo<Tables> pageInfo = new PageInfo<>(tablesList);
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("endPage", pageInfo.getNavigateLastPage());
        map.put("curPage", tables.getPage());
        map.put("data", pageInfo.getList());
        return map;
    }

    @RequestMapping("add")
    @ResponseBody
    public HashMap<String, Object> add(Tables tables) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Tables> wrapper = new QueryWrapper<>();
        wrapper.eq("table_name", tables.getTableName());
        if (tablesService.list(wrapper).isEmpty()) {
            int result = tablesService.add(tables);
            if (result > 0) {
                map.put("info", "新增成功");
            } else {
                map.put("info", "新增失败");
            }
        } else {
            map.put("info", "餐桌已存在");
        }
        return map;
    }

    @RequestMapping("update")
    @ResponseBody
    public HashMap<String, Object> update(Tables tables) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Tables> wrapper = new QueryWrapper<>();
        wrapper.eq("table_name", tables.getTableName());
        final Tables tables_db = tablesService.getOne(wrapper);
        if (tables_db == null || !tables.getStatus().equals(tables_db.getStatus())) {
            boolean result = tablesService.updateById(tables);
            if (result) {
                map.put("info", "修改成功");
            } else {
                map.put("info", "修改失败");
            }
        } else {
            map.put("info", "餐桌已存在");
        }
        return map;
    }

    @RequestMapping("del")
    @ResponseBody
    public HashMap<String, Object> del(Tables Tables) {
        HashMap<String, Object> map = new HashMap<>();
        int result = tablesService.delete(Tables);
        if (result > 0) {
            map.put("info", "删除成功");
        } else {
            map.put("info", "删除失败");
        }
        return map;
    }

    @RequestMapping("fuzzySelect")
    @ResponseBody
    public HashMap<String, Object> fuzzySelect(Tables tables) {
        HashMap<String, Object> map = new HashMap<>();
        if (tables.getTableName().isEmpty() && tables.getId() == null && tables.getStatus() == null) {
            map.put("info", "请输入信息以查询");
        } else {
            //1 设置分页参数：页码和条数
            PageHelper.startPage(tables.getPage(), tables.getRows());
            //2 查询结果集合
            List<Tables> tablesList = tablesService.select(tables);
            // 3 创建分页对象
            PageInfo<Tables> pageInfo = new PageInfo<>(tablesList);
            map.put("total", pageInfo.getTotal());
            map.put("pages", pageInfo.getPages());
            map.put("endPage", pageInfo.getNavigateLastPage());
            map.put("curPage", tables.getPage());
            if (pageInfo.getList().isEmpty()) {
                map.put("info", "没有此条记录，请添加！");
            } else {
                map.put("data", pageInfo.getList());
                map.put("info", "查询成功");
            }
        }
        return map;
    }
}
