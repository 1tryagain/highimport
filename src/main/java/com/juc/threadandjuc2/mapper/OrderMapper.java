package com.juc.threadandjuc2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.entity.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liu
 * @create 2023-09-21-21:57
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    @Select({"SELECT o.order_no, SUM(i.price * i.count) AS amount",
            "FROM t_order o JOIN t_order_item i ON o.order_no = i.order_no",
            "GROUP BY o.order_no"})
    List<OrderVo> getOrderAmount();

    @Update({"UPDATE t_order t LEFT JOIN",
            "(SELECT price*count as amount,order_no FROM t_order_item) t1 ",
            "on t.order_no=t1.order_no",
            "SET t.amount= t1.amount;"})
    void updateOrderAmount();
}