package com.zx.thread;

import com.zx.util.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 不建议的操作
 * 暂停 suspend 调用后，线程不会释放已经占有得资源（比如锁），而是占有着资源进入睡眠状态，容易引发死锁
 * 恢复 resume
 * 停止 stop 终结一个线程得时候不会保证线程得资源正常释放
 */
public class Deprecated {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();

        SleepUtils.second(3);
        printThread.suspend();
        System.out.println("main suspend PrintThread at " + format.format(new Date()));

        SleepUtils.second(3);
        printThread.resume();
        System.out.println("main resume PrintThread at " + format.format(new Date()));

        SleepUtils.second(3);
        printThread.stop();
        System.out.println("main stop PrintThread at " + format.format(new Date()));

        SleepUtils.second(3);
    }

    static class Runner implements Runnable{

        @Override
        public void run() {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            while (true){
                System.out.println(Thread.currentThread().getName() + "Run at " +
                        format.format(new Date()));
                SleepUtils.second(1);
            }
        }
    }
}
