package com.zx.thread;

import com.zx.util.SleepUtils;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 通过中断安全地终止线程
 * 利用一个boolean变量控制是否需要停止任务并终止线程
 */
public class Shutdown {
    public static void main(String[] args) {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();

        // 睡眠一秒，main线程对countThread进行中断，使countThread线程能够感知中断而结束
        SleepUtils.second(1);
        countThread.interrupt();

        // 两种方式终止线程
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        // 睡眠一秒，main线程对Runner two进行中断，使countThread线程能够感知中断而结束
        SleepUtils.second(1);
        two.cancel();
    }

    // 不断对变量进行累加
    private static class Runner implements Runnable{

        private long i;
        private volatile boolean on = true;
        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancel(){
            on = false;
        }
    }
}
