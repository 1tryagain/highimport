package com.juc.threadandjuc2.Utils;

import com.juc.threadandjuc2.entity.Order;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author liu
 * @create 2023-09-29-22:08
 */
public class RO {
     public static void main(String[] args) {
          List<Order> data = getData();
          List<List<Order>> partition = Spilt.partition(data, 10);
          System.out.println(partition);
     }
     public static List<Order> getData(){
          List<Order> list=new LinkedList<>();
          for (int i = 0; i < 5000; i++) {
               Order order= new Order();
               order.setOrderNo("Order0929"+i);
               order.setUserId(1L+(long) (new Random().nextDouble()*2));
               order.setAmount(new BigDecimal(new Random().nextInt(100)+1));
               list.add(order);
          }
          return list;
     }
}
