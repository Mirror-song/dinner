package com.hqyj.dinner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dinner.entity.Food;
import com.hqyj.dinner.mapper.FoodMapper;
import com.hqyj.dinner.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    @Resource
    FoodMapper foodMapper;
    @Override
    public List<Food> queryAllFood() {
        return foodMapper.selectList(null);
    }

    @Override
    public int add(Food food) {
        return foodMapper.insert(food);
    }

    @Override
    public int modify(Food food) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",food.getId());
        return foodMapper.update(food,queryWrapper);
    }

    @Override
    public int delete(Food food) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",food.getId());
        return foodMapper.delete(queryWrapper);
    }

    @Override
    public List<Food> select(Food food) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        if(food.getTypeId()!=null){
            queryWrapper.eq("type_id",food.getTypeId());
        }
        if(food.getFoodName()!=null){
            queryWrapper.like("food_name",food.getFoodName());
        }
        return foodMapper.selectList(queryWrapper);
    }

    @Override
    public Page<Food> queryMenuFoodPage(Page<Object> objectPage) {
        return foodMapper.queryMenuFoodPage(objectPage);
    }
}
