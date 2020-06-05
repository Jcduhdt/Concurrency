package com.zx.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 线程七态
 * 1.初始态：线程被new了出来
 * 2.就绪态：线程调用了start
 * 3.运行态：线程获取了时间片，正在进行处理
 * 4.等待状态：等待其它线程做出特定动作，比如线程调用了wait()方法，进入等待状态，需要其他线程将其唤醒
 * 5.超时等待状态：可在指定得时间自行返回，在等待状态基础上增加了超时限制，超时时间达到时会回到就绪状态
 * 6.阻塞状态：线程阻塞于锁
 * 7.终止态：当前线程执行完毕方法
 */
public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(),"TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();
    }

    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            // 这个线程就一直循环睡眠等待
            // java.lang.Thread.State: TIMED_WAITING (sleeping)
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Waiting implements Runnable {
        @Override
        public void run() {
            // 该线程一直循环等待
            // java.lang.Thread.State: WAITING (on object monitor)
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Blocked implements Runnable{
        @Override
        public void run() {
            // block1获取锁，进入超时等待
            // java.lang.Thread.State: TIMED_WAITING (sleeping)
            // block2等待获取锁
            // java.lang.Thread.State: BLOCKED (on object monitor)
            synchronized (Blocked.class){
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
