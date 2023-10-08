package com.juc.threadandjuc2.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liu
 * @create 2023-10-08-19:36
 */
@Data
public class OrderVo {
    private String orderNo;
    private BigDecimal amount;
}