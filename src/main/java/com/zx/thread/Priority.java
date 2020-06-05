package com.zx.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 线程优先级从1->10递增，默认为5
 * 不同JVM以及操作系统上，线程规划存在差异
 * 有些操作系统甚至会忽略对线程优先级的设定
 * 从结果显示来看，优先级高和优先级低的线程所得到的结果差不多
 * 说明线程优先级没有生效，程序正确性不能依赖于线程的优先级高低
 *
 * 但是我们在设置线程优先级的时候
 * 1.针对频繁阻塞的线程需要设置较高优先级
 * 2.偏重计算的线程设置较低优先级
 */
public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread: " + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobs) {
            System.out.println("Job Priority : " + job.priority + ", Count :" + job.jobCount);
        }
    }

    static class Job implements Runnable{
        private int priority;
        private long jobCount;
        public Job(int priority){
            this.priority = priority;
        }

        @Override
        public void run(){
            while (notStart){
                Thread.yield();
            }
            while (notEnd){
                // 不加这个yield，会计算得更多，加yield就是查看操作系统会不会
                // 根据线程优先级安排时间片吧
                Thread.yield();
                jobCount++;
            }
        }


        /*
        结果
        Job Priority : 1,Count :5675144
        Job Priority : 1,Count :5706254
        Job Priority : 1,Count :5642955
        Job Priority : 1,Count :5613348
        Job Priority : 1,Count :5675210
        Job Priority : 10,Count :5780087
        Job Priority : 10,Count :5841030
        Job Priority : 10,Count :5797686
        Job Priority : 10,Count :5696282
        Job Priority : 10,Count :5757227
        */
    }
}
