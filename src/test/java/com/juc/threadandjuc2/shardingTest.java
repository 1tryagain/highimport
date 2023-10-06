package com.juc.threadandjuc2;

import com.juc.threadandjuc2.entity.Dict;
import com.juc.threadandjuc2.entity.Order;
import com.juc.threadandjuc2.mapper.DictMapper;
import com.juc.threadandjuc2.mapper.OrderMapper;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liu
 * @create 2023-09-22-16:22
 */
@SpringBootTest
public class shardingTest {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DictMapper dictMapper;
    @Test
    public void testInsertOrder(){

        Order order = new Order();
        order.setOrderNo("test001");
        order.setUserId(1L);
        order.setAmount(new BigDecimal(100));
        orderMapper.insert(order);
    }
    @Test
    public void testInsertOrderDatabaseStrategy(){


        for (long i = 0; i < 4; i++) {
            Order order = new Order();
            order.setOrderNo("test00"+i);
            order.setUserId(1L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
        for (long i = 4; i < 8; i++) {
            Order order = new Order();
            order.setOrderNo("test00"+i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }

    }
    @Test
    public void select(){
        List<Order> orders = orderMapper.selectList(null);
        orders.forEach(System.out::println);
    }

    @Test
    public void testBroadcast(){

        Dict dict = new Dict();
        dict.setDictType("type1");
        dictMapper.insert(dict);
    }

    /**
     * 查询操作，只从一个节点获取数据
     * 随机负载均衡规则
     */
    @Test
    public void testSelectBroadcast(){

        List<Dict> dicts = dictMapper.selectList(null);
        dicts.forEach(System.out::println);
    }

    @Test
    public void testInsertOrderDatabase(){

        HintManager hintManager = HintManager.getInstance();
        //hintManager.addDatabaseShardingValue("t_order",0);
//        hintManager.addDatabaseShardingValue("t_order",1);
//        hintManager.addTableShardingValue("t_order",0);
//        hintManager.addTableShardingValue("t_order",1);
        hintManager.setDatabaseShardingValue(1);
        for (long i = 0; i < 4; i++) {
            Order order = new Order();
            order.setOrderNo("test00"+i);
            order.setUserId(1L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
        for (long i = 4; i < 8; i++) {
            Order order = new Order();
            order.setOrderNo("test00"+i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
        hintManager.close();
    }
}
