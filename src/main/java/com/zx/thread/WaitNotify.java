package com.zx.thread;

import com.zx.util.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 主要是熟悉wait和notify
 * wait和notify都要用锁住的哪个对象进行使用
 * 想着靠锁来进行自我控制就对了
 */
public class WaitNotify {
    static boolean flag =true;
    static Object lock = new Object();

    public static void main(String[] args) {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        SleepUtils.second(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{

        @Override
        public void run() {
            // 加锁，拥有lock得Monitor
            synchronized (lock){
                // 当条件不满足得时候，继续wait，同时释放了lock锁
                while(flag){
                    try{
                        System.out.println(Thread.currentThread() + "flag is true. wait@"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    }catch (InterruptedException e){
                    }
                }
                // 条件满足，完成工作
                System.out.println(Thread.currentThread() + "flag is false. running@"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
    static class Notify implements Runnable{

        @Override
        public void run() {
            // 加锁，拥有lock得Monitor
            synchronized (lock){
                // 获取lock的锁，然后进行通知，通知时不会释放lock的锁
                // 知道当前线程释放了lock锁后，WaitThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + "hold lock. notify@"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
                // 再次加锁
                // 这是锁可重入了？
                synchronized (lock){
                    System.out.println(Thread.currentThread() + "hold lock again. sleep@"
                            + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    SleepUtils.second(5);
                }

            }
        }
    }
}
