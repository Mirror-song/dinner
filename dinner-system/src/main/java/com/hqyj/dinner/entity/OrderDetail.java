package com.hqyj.dinner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDetail extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单详情ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 所属订单ID
     */
    //private Integer orderId;

    /**
     * 菜名ID
     */
    private Integer foodId;

    /**
     * 订餐数量
     */
    private Integer foodCount;

    /**
     * 状态，0：删除，1：正常
     */
    private Integer tableId;

    private Integer status;

    private Double vipPrice;

    private Double price;

    /**
     * 备用
     */
    private Integer pay;


}
