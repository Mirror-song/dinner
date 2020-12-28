package com.hqyj.dinner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqyj.dinner.entity.FoodType;
import com.hqyj.dinner.mapper.FoodTypeMapper;
import com.hqyj.dinner.service.FoodTypeService;
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
public class FoodTypeServiceImpl extends ServiceImpl<FoodTypeMapper, FoodType> implements FoodTypeService {

    @Resource
    FoodTypeMapper foodTypeMapper;
    @Override
    public List<FoodType> findAll() {
        return foodTypeMapper.selectList(null);
    }

    @Override
    public int add(FoodType foodType) {
        return foodTypeMapper.insert(foodType);
    }

    @Override
    public int update(FoodType foodType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",foodType.getId());
        return foodTypeMapper.update(foodType,queryWrapper);
    }

    @Override
    public int delete(FoodType foodType) {
        QueryWrapper<FoodType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",foodType.getId());
        return foodTypeMapper.delete(queryWrapper);
    }

    @Override
    public List<FoodType> select(FoodType foodType) {
        QueryWrapper<FoodType> queryWrapper = new QueryWrapper<>();
        if(foodType.getId()!=null){
            queryWrapper.eq("id",foodType.getId());
        }
        if(foodType.getTypeName()!=null){
            queryWrapper.like("type_name",foodType.getTypeName());
        }
        return foodTypeMapper.selectList(queryWrapper);
    }
}
