package com.zx.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * Exchanger数据交换，校对工作
 * 如果两个线程有一个没有执行exchange方法，则会一直等待，若担心特殊情况发生，可设置最大等待时长
 */
public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String A = "银行流水A";
                    exgr.exchange(A);
                }catch (Exception e){

                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String B = "银行流水B";
                    String A = exgr.exchange("B");
                    System.out.println("A和B的数据是否一致：" + A.equals(B) + ",A录入的是：" + A + ",B录入的是："+B);
                }catch (Exception e){

                }
            }
        });
        threadPool.shutdown();
    }
}
