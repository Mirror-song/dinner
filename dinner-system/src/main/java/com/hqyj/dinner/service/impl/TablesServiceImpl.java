package com.hqyj.dinner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqyj.dinner.entity.Tables;
import com.hqyj.dinner.mapper.TablesMapper;
import com.hqyj.dinner.service.TablesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Luo
 * @since 2020-10-27
 */
@Service
public class TablesServiceImpl extends ServiceImpl<TablesMapper, Tables> implements TablesService {

    @Autowired
    TablesMapper tablesMapper;

    @Override
    public int add(Tables tables) {
        return tablesMapper.insert(tables);
    }

    @Override
    public int delete(Tables tables) {
        QueryWrapper<Tables> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", tables.getId());
        return tablesMapper.delete(queryWrapper);
    }

    @Override
    public List<Tables> select(Tables tables) {
        QueryWrapper<Tables> queryWrapper = new QueryWrapper<>();
        if (tables.getId() != null) {
            queryWrapper.eq("id", tables.getId());
        }
        if (!tables.getTableName().isEmpty()) {
            queryWrapper.like("table_name", tables.getTableName());
        }
        if (tables.getStatus() != null) {
            queryWrapper.eq("status", tables.getStatus());
        }
        return tablesMapper.selectList(queryWrapper);
    }
}
