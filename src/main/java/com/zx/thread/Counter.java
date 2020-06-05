package com.zx.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-04
 * 改代码实现了一个基于CAS线程安全的计数器方法safeCount和一个非线程安全的计数器count
 */
public class Counter {

    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        cas.count();
                        cas.safeCount();
                    }
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
        // 等待所有线程执行完成
        for (Thread t : ts) {
            try{
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("cas.i = " + cas.i);
        System.out.println("cas.atomicI = " + cas.atomicI.get());
        System.out.println(System.currentTimeMillis()-start + "ms");
    }

    /**
     * 使用cas实现线程安全计数器
     */
    private void safeCount() {
        for(;;){
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if(suc){
                break;
            }
        }
        // 该句即等于上面这个for循环，且使用这句替换上面，耗时会少不少
        // atomicI.incrementAndGet();
    }

    /**
     * 线程安全计数器
     */
    private void count() {
        i++;
    }
}
