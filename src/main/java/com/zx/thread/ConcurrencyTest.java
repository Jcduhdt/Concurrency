package com.zx.thread;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-04
 * 多线程一定比单线程快吗
 * 测试上下文切换耗时
 */
public class ConcurrencyTest {
    private static final long count = 100000001;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    /**
     * 串行
     */
    private static void serial() {
        long start = System.currentTimeMillis();
        long a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial: " + time + "ms,b= " + b + ",a = " + a);
    }

    /**
     * 并发
     * @throws InterruptedException
     */
    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long  a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        thread.join();
        long time = System.currentTimeMillis() - start;
        System.out.println("concurrency: " + time + "ms,b= " + b);
    }
}
