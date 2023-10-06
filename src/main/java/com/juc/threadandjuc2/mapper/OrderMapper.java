package com.juc.threadandjuc2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juc.threadandjuc2.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author liu
 * @create 2023-09-21-21:57
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {
}