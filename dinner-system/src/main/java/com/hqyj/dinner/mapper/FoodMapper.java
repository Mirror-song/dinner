package com.hqyj.dinner.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dinner.entity.Food;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
public interface FoodMapper extends BaseMapper<Food> {
    @Select("SELECT food.*,food_type.type_name FROM food INNER JOIN food_type ON food.type_id = food_type.id WHERE food.`status` =1")
    Page<Food> queryMenuFoodPage(Page<Object> objectPage);
}
