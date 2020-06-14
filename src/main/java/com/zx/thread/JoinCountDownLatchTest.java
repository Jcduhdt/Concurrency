package com.zx.thread;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * 不停检查join线程是否存活
 * 存活则等待
 */
public class JoinCountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parse1 finish");
            }
        });
        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2 finish");
            }
        });
        parser1.start();
        parser2.start();
        parser1.join();
        parser2.join();
        System.out.println("all parse finish");
    }
}
