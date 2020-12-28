package com.hqyj.dinner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class User extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String telNum;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 状态，0：逻辑删除，1：正常
     */
    private Integer status;

    /**
     * 备用字段
     */
    private Integer roleId;


}
