package com.zx.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * CyclicBarrier的构造器参数中，还有一个传Thread的线程
 * 作用是，当这么多线程到达屏障时，优先执行该任务，再执行其他任务
 */
public class CyclicBarrierTest2 {
    static CyclicBarrier c = new CyclicBarrier(2,new A());

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        }).start();

        try {
            c.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(2);

    }

    static class A implements Runnable{

        @Override
        public void run() {
            System.out.println(3);
        }
    }
}
