package com.juc.threadandjuc2.service;

import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.entity.OrderItem;
import com.juc.threadandjuc2.mapper.OrderItemMapper;
import com.juc.threadandjuc2.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author liu
 * @create 2023-10-08-16:23
 */
public class OrderItemFuture implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemFuture.class);
    private OrderItemMapper orderItemMapper;
    private OrderMapper orderMapper;
    private List<Order> list;
    private CountDownLatch countDownLatch;

    public OrderItemFuture(CountDownLatch countDownLatch,OrderItemMapper orderItemMapper,OrderMapper orderMapper,List<Order> list){
        this.countDownLatch=countDownLatch;
        this.orderItemMapper=orderItemMapper;
        this.list=list;
        this.orderMapper=orderMapper;
    }
    @Override
    public Integer call() throws Exception {
        Integer count=0;
        long start = System.currentTimeMillis();
        logger.info(Thread.currentThread().getName()+" 开始插入订单详情数据");
        for (Order order:list){
            OrderItem orderItem=new OrderItem();
            orderItem.setId(order.getId());
            orderItem.setOrderNo(order.getOrderNo());
            orderItem.setCount(new Random().nextInt(10)+1);
            orderItem.setPrice(new BigDecimal(new Random().nextInt(20)+1));
            orderItem.setUserId(order.getUserId());
            orderItemMapper.insert(orderItem);
//            order.setAmount(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));
//            orderMapper.update(order,null);
            //执行次数
            count++;
        }
        long end = System.currentTimeMillis();
        logger.info(Thread.currentThread().getName()+" 插入完成，用时: "+(end-start));
        if (countDownLatch!=null){ countDownLatch.countDown();}
        return count;
    }
}
