package com.juc.threadandjuc2.service;

import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.mapper.OrderMapper;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author liu
 * @create 2023-09-29-19:49
 */
public class OrderFuture2 implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(OrderFuture2.class);
    private OrderMapper orderMapper;
    private List<Order> list;
    private CountDownLatch countDownLatch;
    public OrderFuture2(CountDownLatch countDownLatch, OrderMapper orderMapper, List<Order> list){
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
        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("t_order",0);
        hintManager.addTableShardingValue("t_order",1);
        for (Order order:list){
           orderMapper.insert(order);
           //执行次数
           count++;
        }
        hintManager.close();
        long end = System.currentTimeMillis();
        logger.info(Thread.currentThread().getName()+" 插入完成，用时: "+(end-start));
        if (countDownLatch!=null){ countDownLatch.countDown();}
        return count;
    }
}
