package com.juc.threadandjuc2.service;

import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author liu
 * @create 2023-09-29-19:49
 * 分库分表
 */
public class OrderFuture implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(OrderFuture.class);
    private OrderMapper orderMapper;
    private List<Order> list;
    private CountDownLatch countDownLatch;
    public OrderFuture(CountDownLatch countDownLatch,OrderMapper orderMapper,List<Order> list){
        this.countDownLatch=countDownLatch;
        this.orderMapper=orderMapper;
        this.list=list;
    }
      //要使用countdownlatch 和 list
    @Override
    public Integer call() throws Exception {
        Integer count=0;
        long start = System.currentTimeMillis();
        logger.info(Thread.currentThread().getName()+" 开始插入订单数据");
        for (Order order:list){
           orderMapper.insert(order);
           //执行次数
           count++;
        }
        long end = System.currentTimeMillis();
        logger.info(Thread.currentThread().getName()+" 插入完成，用时: "+(end-start));
        if (countDownLatch!=null){ countDownLatch.countDown();}
        return count;
    }
}
