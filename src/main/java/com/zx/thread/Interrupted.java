package com.zx.thread;

import com.zx.util.SleepUtils;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 中断：线程得标识位属性，表示一个运行中的饿线程是否被其他线程进行了中断操作
 * 线程通过方法isInterrupted()判断是否被中断
 * 许多方法在抛出InterruptedException之前会先将该线程得中断标志位清除
 * 本代码对两种线程进行中断操作
 */
public class Interrupted {
    public static void main(String[] args) {
        // sleepThread不停得尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        // busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();
        // 休眠5秒，让sleepThread和busyThread充分运行
        SleepUtils.second(5);

        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("busyThread interrupted is " + busyThread.isInterrupted());
        // 防止sleepThread和busyThread立刻退出
        SleepUtils.second(2);
    }

    static class SleepRunner implements Runnable{

        @Override
        public void run() {
            while (true){
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable{

        @Override
        public void run() {
            while (true){

            }
        }
    }
}
