package com.juc.threadandjuc2.timer;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liu
 * @create 2023-12-02-18:45
 */
public class CacheByFuture<T> {
    //适用于并发的hashMap
    ConcurrentHashMap<String, FutureTask<T>> concurrentHashMap = new ConcurrentHashMap<String,FutureTask<T>>();

    public T get(String key,Callable<T> callable){
        T result = null;
        //ConcurrentHashMap本身就是支持并发操作的 所以读写的时候不需要加锁，内部实现已经加锁处理。
        FutureTask<T> task = concurrentHashMap.get(key);
        if(task==null){
            FutureTask<T> tempTask = new FutureTask<T>(callable);
            /**
             * putIfAbsent：
             * 如果没有这个key，那么放入key-value，返回null。
             * 如果有这个key，那么返回value。
             * 整个操作时原子性的，因为内部实现加锁了。
             */
            /**
             * 缓存的意义在于：一定要确保map中保存的task已经执行完成，通过get方法直接可以取出计算好的结果来。如果单纯的就是
             * put进去一个没有执行的task，没有任何意义。所以使用putIfAbsent，第一次放进task之后去执行这个task，以后就不执行了。
             */
            task = concurrentHashMap.putIfAbsent(key, tempTask);
            if(task==null){
                task = tempTask;
                System.out.println("key="+key+" 开始计算........");
                task.run();
            }
        }

        try {
            System.out.println("开始去cache中获取"+key+" 的结果");
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //如果get被中断，那么直接取消任务
            task.cancel(true);
            //此时暂时用不上，但是还是写上吧
            //Thread.currentThread().interrupt();
            concurrentHashMap.remove(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
            concurrentHashMap.remove(key);
        } catch(Exception e){
            e.printStackTrace();
            concurrentHashMap.remove(key);
        }

        return result;

    }



    /**
     *
     */
    public static void main(String[] args) {
        CacheByFuture<String> tc = new CacheByFuture<String>();
        tc.start();
    }


    /**
     * 测试 用FutureTask、ConcurrentMap实现的缓存示例
     */
    public void start(){
        CacheByFuture<String> tc = new CacheByFuture<String>();
        ExecutorService es = Executors.newFixedThreadPool(100);
        for(int i=1;i<=50;i++){
            final int key = (int) (Math.random()*10);
            es.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random()*100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String result = tc.get(String.valueOf(key), new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            Thread.sleep(3*1000);
                            return "complete "+new Date().toLocaleString();
                        }
                    });
                    System.out.println(Thread.currentThread().getName()+"  获取完成。。。 结果："+result);
                }
            });
        }

    }





}