package com.zx.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * isBroken方法用来了解线程是否被中断
 */
public class CyclicBarrierTest3 {
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.interrupt();
        try {
            c.await();
        } catch (Exception e) {
            System.out.println(c.isBroken());
        }
    }
}
