package com.juc.threadandjuc2;

import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.entity.User;
import com.juc.threadandjuc2.mapper.OrderMapper;
import com.juc.threadandjuc2.mapper.UserMapper;
import com.juc.threadandjuc2.timer.FutureTask1;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testInsert(){

        User user = new User();
        user.setUname("张三");
        userMapper.insert(user);
    }
    @Test
    public void testSelect(){
        User user = userMapper.selectById(2);
        User user1 = userMapper.selectById(2);
        User user2 = userMapper.selectById(2);
        System.out.println(user.getUname());
        System.out.println(user1.getUname());
        System.out.println(user2.getUname());
    }

    @Test
    public void testSelectFromOrderAndUser(){
        User user = userMapper.selectById(1L);
        Order order = orderMapper.selectById(1L);
    }

}