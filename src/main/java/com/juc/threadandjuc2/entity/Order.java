package com.juc.threadandjuc2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liu
 * @create 2023-09-21-21:51
 */
@TableName("t_order")//逻辑表名
@Data
public class Order {
    //使用shardingsphere的分布式序列
    @TableId(type = IdType.AUTO)//依赖数据库的主键自增策略
    //@TableId(type = IdType.ASSIGN_ID)//分布式id
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal amount;
}