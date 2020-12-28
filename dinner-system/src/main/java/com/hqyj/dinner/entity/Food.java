package com.hqyj.dinner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.beans.Transient;
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
public class Food extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜品名称
     */
    private String foodName;
    /**
     * 菜品名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 菜系id
     */
    private Integer typeId;

    /**
     * 价格
     */
    private Double price;

    /**
     * vip价格
     */
    private Double vipPrice;

    /**
     * 菜品图片url
     */
    private String imgUrl;

    /**
     * 菜品描述
     */
    private String description;

    /**
     * 状态，0：下架，1：上架
     */
    private Integer status;

    /**
     * 备用
     */
    private String zhanWei;


}
