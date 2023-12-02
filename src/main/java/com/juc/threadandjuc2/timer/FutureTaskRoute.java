package com.juc.threadandjuc2.timer;

import com.juc.threadandjuc2.Utils.RO;
import com.juc.threadandjuc2.Utils.Spilt;
import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.mapper.OrderItemMapper;
import com.juc.threadandjuc2.mapper.OrderMapper;
import com.juc.threadandjuc2.service.OrderFuture;
import com.juc.threadandjuc2.service.OrderFuture2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liu
 * @create 2023-12-02-21:37
 */
@Service
public class FutureTaskRoute {
    private static final Logger logger = LoggerFactory.getLogger(FutureTaskRoute.class);
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    private AtomicInteger result=new AtomicInteger(0);

    static CacheByFuture<Integer> cache = new CacheByFuture<Integer>();

    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,
            10,
            10L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());
    //强制路由
    public void highimport2() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Order> data = RO.getData();
        List<List<Order>> partition = Spilt.partition(data, 100);
        int size=data.size();
        int n=size%100==0? size/100 : size/100+1;
        CountDownLatch countDownLatch=new CountDownLatch(n);
        try {

            for (List<Order> list : partition) {
              //  Future<Integer> submit = threadPool.submit(new OrderFuture2(countDownLatch, orderMapper, list));
                Future<Integer> submit = threadPool.submit(new OrderFuture2(countDownLatch, orderMapper, list));
                Integer res = submit.get();
                result.set(res+result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        long end = System.currentTimeMillis();
        countDownLatch.await();
        logger.info("插入成功，总用时为："+(end-start));
        logger.info("插总数量为："+result.get());
    }
    //修改order的Amount

}
