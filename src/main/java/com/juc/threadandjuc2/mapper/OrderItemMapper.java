package com.juc.threadandjuc2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juc.threadandjuc2.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author liu
 * @create 2023-09-29-16:35
 */
@Mapper
@Repository
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
