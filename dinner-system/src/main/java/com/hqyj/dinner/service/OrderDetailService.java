package com.hqyj.dinner.service;

import com.hqyj.dinner.entity.OrderDetail;
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
public interface OrderDetailService extends IService<OrderDetail> {

    List<OrderDetail> findAll();

    int add(OrderDetail  orderDetail );

    int delete(OrderDetail  orderDetail);

    int update(OrderDetail  orderDetail);

    List<OrderDetail> select(OrderDetail  orderDetail);

    List<OrderDetail> selectTableOrder(Integer tableId);

    List<OrderDetail> selectNotPay(Integer tableId);

    List<OrderDetail> selectNotMake(OrderDetail orderDetail);

    List<OrderDetail> selectOntMakeFood(OrderDetail orderDetail);

    int update2(OrderDetail orderDetail);
}
