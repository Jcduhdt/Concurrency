package com.zx.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * CyclicBarrier,构造参数表示屏障拦截的线程数量
 * await方法表示告诉CyclicBarrier我已经到达该屏障，当前线程被阻塞
 * 直到给定数量线程到达，则执行后面
 */
public class CyclicBarrierTest {
    // 若传参为3，则两个线程均不会执行
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
                System.out.println("1");
            }
        }).start();
        try {
            c.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }
}
