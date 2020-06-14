package com.zx.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * Semaphore 允许多少个线程同时做什么事
 * 其实这个例子看不出来吧
 */
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获取许可
                        s.acquire();
                        System.out.println("save data");
                        // 释放许可
                        s.release();
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}
