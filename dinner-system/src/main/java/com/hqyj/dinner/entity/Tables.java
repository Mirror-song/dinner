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
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Tables extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 餐桌ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 餐桌名
     */
    private String tableName;

    /**
     * 状态： 0：被预定  1：空闲
     */
    private Integer status;

    /**
     * 可坐人数
     */
    private Integer number;

    /**
     * 备用
     */
    private String zhanWei;


}
