package com.zx.thread;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-04
 * 演示死锁
 * 方法一：
 *      可以终端输入jconsole，查看该进程，查看线程，最下方那有个检测死锁
 * 方法二：
 *      终端输入jps，查看该java文件对应进程，比如为2876 DeadLockDemo
 *      然后输入jstack 2876 就可以看到信息，最下端有死锁信息
 * 方法三：
 *      直接运行装的插件VisualVM，选择进程，在Thread那会出现Deadlock detected!字样，
 *      根据提示可以查看详细信息
 */
public class DeadLockDemo {
    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println("1");
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
