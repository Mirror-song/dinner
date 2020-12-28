package com.hqyj.dinner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hqyj.dinner.entity.OrderDetail;
import com.hqyj.dinner.mapper.OrderDetailMapper;
import com.hqyj.dinner.service.OrderDetailService;
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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Resource
    OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailMapper.selectList(null);
    }

    @Override
    public int add(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail);
    }

    @Override
    public int delete(OrderDetail orderDetail) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("food_id",orderDetail.getFoodId());
        return orderDetailMapper.delete(wrapper);
    }

    @Override
    public int update(OrderDetail orderDetail) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("status",2);
        wrapper.eq("table_id",orderDetail.getTableId());
        orderDetail.setPay(1);
        return orderDetailMapper.update(orderDetail,wrapper);
    }

    @Override
    public List<OrderDetail> select(OrderDetail orderDetail) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("table_id",orderDetail.getTableId());
        wrapper.eq("status",0);
        return orderDetailMapper.selectList(wrapper);
    }

    @Override
    public List<OrderDetail> selectTableOrder(Integer tableId) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_id",tableId);
        queryWrapper.in("status",1,2);
        queryWrapper.eq("pay",0);
        return orderDetailMapper.selectList(queryWrapper);
    }

    @Override
    public List<OrderDetail> selectNotPay(Integer tableId) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_id",tableId);
        queryWrapper.eq("status",2);
        queryWrapper.eq("pay",0);
        return orderDetailMapper.selectList(queryWrapper);
    }

    @Override
    public List<OrderDetail> selectNotMake(OrderDetail orderDetail) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("pay",0);
        return orderDetailMapper.selectList(queryWrapper);
    }

    @Override
    public List<OrderDetail> selectOntMakeFood(OrderDetail orderDetail) {
        QueryWrapper<OrderDetail> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("food_id",orderDetail.getFoodId());
        queryWrapper.eq("status",1);
        queryWrapper.eq("pay",0);
        return orderDetailMapper.selectList(queryWrapper);
    }

    @Override
    public int update2(OrderDetail orderDetail) {
        UpdateWrapper<OrderDetail> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",orderDetail.getId());
        updateWrapper.set("status",2);
        return orderDetailMapper.update(orderDetail,updateWrapper);
    }


}
