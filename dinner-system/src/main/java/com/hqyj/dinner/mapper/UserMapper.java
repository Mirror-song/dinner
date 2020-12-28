package com.hqyj.dinner.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dinner.entity.Food;
import com.hqyj.dinner.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
public interface UserMapper extends BaseMapper<User> {
    List<String> selectRole(String userName);

}
