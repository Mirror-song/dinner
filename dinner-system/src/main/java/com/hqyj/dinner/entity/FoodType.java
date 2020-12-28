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
public class FoodType extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜系ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜系名称
     */
    private String typeName;

    /**
     * 状态，0：下架  1：上架
     */
    private Integer status;


}
