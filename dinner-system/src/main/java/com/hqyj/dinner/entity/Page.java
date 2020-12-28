package com.hqyj.dinner.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


/**
 * <p> 分页<p>
 *
 * @Author : long
 * @Date : 2020-10-26 14:24
 **/
@Data
public class Page {
    //页码
//    @Transient
    @TableField(exist = false)
    private Integer page;
    //每页显示的行数
//    @Transient
    @TableField(exist = false)
    private Integer rows;
}
