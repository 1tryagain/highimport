package com.juc.threadandjuc2;

import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.entity.OrderVo;
import com.juc.threadandjuc2.mapper.OrderMapper;
import com.juc.threadandjuc2.mapper.UserMapper;
import com.juc.threadandjuc2.timer.FutureTask1;
import com.juc.threadandjuc2.timer.OrderItemFutureTask;
import org.apache.calcite.linq4j.OrderedQueryable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author liu
 * @create 2023-09-20-20:43
 */
@SpringBootTest
class test {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private FutureTask1 futureTask1;
    @Autowired
    private OrderItemFutureTask orderItemFutureTask;
    /**
     * 写入数据的测试
     */
    @Test
    public void testOrderInsert() throws InterruptedException {

        futureTask1.highimport();
    }
    @Test
    public void testOrderInsert2() throws InterruptedException {

        futureTask1.highimport2();
    }
    @Test
    public void testOrderItemInsert() throws InterruptedException {
        List<Order> list = orderMapper.selectList(null);
        orderItemFutureTask.highimportOrderItem(list);
    }
    @Test
    public void testOrderVo() {
        orderMapper.updateOrderAmount();
    }

}