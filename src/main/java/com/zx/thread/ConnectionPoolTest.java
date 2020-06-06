package com.zx.thread;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 模拟客户端ConnectionRunner获取使用释放连接的过程
 * 获取链接时增加获取到连接的数量
 * 反之增加未获取到连接的数量
 */
public class ConnectionPoolTest {
    // 创建线程池
    static ConnectionPool pool = new ConnectionPool(10);
    // 保证所有ConnectionRunner能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main线程将会等待所有ConnectionRunner结束后才能继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        // 线程数量，可以修改线程数量进行观察，就客户端数吧
        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        // 每个客户端连接多少次
        int count = 20;
        // 获取到的连接数
        AtomicInteger got = new AtomicInteger();
        // 未获取到的连接数
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        // 所有客户端线程创建完毕，同时开始
        start.countDown();
        // 所有客户端执行完毕
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
    }

    static class ConnectionRunner implements Runnable{

        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try{
                start.await();
            }catch (Exception e){

            }
            while (count > 0){
                try{
                    // 从线程池中获取链接，如果1000ms内无法获取到，将会返回null
                    // 分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if(connection != null){
                        try{
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                    }
                }catch (Exception ex){

                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
