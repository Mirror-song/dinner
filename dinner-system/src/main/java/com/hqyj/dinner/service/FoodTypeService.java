package com.hqyj.dinner.service;

import com.hqyj.dinner.entity.FoodType;
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
public interface FoodTypeService extends IService<FoodType> {

    List<FoodType> findAll();

    int add(FoodType foodType);

    int update(FoodType foodType);

    int delete(FoodType foodType);

    List<FoodType> select(FoodType foodType);
}
