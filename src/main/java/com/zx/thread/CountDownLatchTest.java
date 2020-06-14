package com.zx.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * CountDownLatch作计数器
 * 条件均达到才进行后面
 */
public class CountDownLatchTest {
    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
                c.countDown();
            }
        }).start();
        c.await();
        System.out.println("3");
    }
}
