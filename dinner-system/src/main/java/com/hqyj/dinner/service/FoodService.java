package com.hqyj.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dinner.entity.Food;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hqyj.dinner.entity.FoodType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
public interface FoodService extends IService<Food> {

    List<Food> queryAllFood();

    int add(Food food);

    int modify(Food food);

    int delete(Food food);

    List<Food> select(Food food);

    Page<Food> queryMenuFoodPage(Page<Object> objectPage);
}
