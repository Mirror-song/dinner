package com.hqyj.dinner.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.dinner.entity.Food;
import com.hqyj.dinner.service.FoodService;
import com.hqyj.dinner.service.OrderDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/food")
public class FoodController {
    @Resource
    private FoodService foodService;
    @Resource
    private OrderDetailService orderDetailService;

    /**
     * 进入菜品管理页面
     */
    @RequestMapping("/foodPage")
    public String foodPage() {
        return "food_manage/food";
    }

    @RequestMapping("/foodType")
    public String foodType() {
        return "food_manage/food_type";
    }

    /**
     * 进入菜单
     */
    @RequestMapping("menuPage")
    public String menuPage() {
        return "menu";
    }

    /**
     * 查询所有菜品（包括已下架菜品）
     */
    @ResponseBody
    @RequestMapping("queryAllFood")
    public HashMap<String, Object> queryAllFood(Food food) {
        HashMap<String, Object> map = new HashMap<>();
        //1 设置分页参数：页码和条数
        PageHelper.startPage(food.getPage(), food.getRows());
        //2 查询结果集合
        List<Food> foodList = foodService.queryAllFood();
        // 3 创建分页对象
        PageInfo<Food> pageInfo = new PageInfo<>(foodList);
        List<Food> listResult = pageInfo.getList();
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("endPage", pageInfo.getNavigateLastPage());
        map.put("curPage", food.getPage());
        map.put("data", listResult);
        return map;
    }

    /**
     * 查询菜单页菜品（排除已下架菜品）
     */
    @ResponseBody
    @RequestMapping("queryMenuFood")
    public HashMap<String, Object> queryMenuFood(int page, int limit) {
        HashMap<String, Object> map = new HashMap<>();
        //mybatisPlus分页
        Page<Food> foodPage = foodService.queryMenuFoodPage(new Page<>(page, limit));
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", foodPage.getTotal());
        map.put("data", foodPage.getRecords());
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, Object> add(Food food) {
        HashMap<String, Object> map = new HashMap<>();
        int result = foodService.add(food);
        if (result > 0) {
            map.put("info", "新增成功");
        } else {
            map.put("info", "新增失败");
        }
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public HashMap<String, Object> update(Food food) {
        HashMap<String, Object> map = new HashMap<>();
        int result = foodService.modify(food);
        if (result > 0) {
            map.put("info", "修改成功");
        } else {
            map.put("info", "修改失败");
        }
        return map;
    }

    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String, Object> del(Food food) {
        HashMap<String, Object> map = new HashMap<>();
        int result = foodService.delete(food);
        if (result > 0) {
            map.put("info", "删除成功");
        } else {
            map.put("info", "删除失败");
        }
        return map;
    }

    @RequestMapping("/select")
    @ResponseBody
    public HashMap<String, Object> select(Food food) {
        HashMap<String, Object> map = new HashMap<>();
        //1 设置分页参数：页码和条数
        PageHelper.startPage(food.getPage(), food.getRows());
        //2 查询结果集合
        List<Food> foodList = foodService.select(food);
        // 3 创建分页对象
        PageInfo<Food> pageInfo = new PageInfo<>(foodList);
        map.put("data", pageInfo.getList());
        List<Food> listResult = pageInfo.getList();
        for (Food e : listResult) {
            System.out.println(e.toString());
        }
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("endPage", pageInfo.getNavigateLastPage());
        map.put("curPage", food.getPage());
        map.put("data", pageInfo.getList());
        return map;
    }
}
