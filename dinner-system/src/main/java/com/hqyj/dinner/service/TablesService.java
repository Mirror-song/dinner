package com.hqyj.dinner.service;

import com.hqyj.dinner.entity.Tables;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Luo
 * @since 2020-10-27
 */
public interface TablesService extends IService<Tables> {
    int add(Tables tables);

    int delete(Tables tables);

    List<Tables> select(Tables tables);
}
