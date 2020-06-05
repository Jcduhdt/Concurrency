package com.zx.thread;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * Thread.join()的使用
 * 线程A实行thread.join()当前线程A等待thread线程终止之后才从thread.join()返回
 * 线程终止时，会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线程
 */
public class Join {

    /**
     * 每个线程调用前一个线程的join()
     * 0  terminate.
     * 1  terminate.
     * 2  terminate.
     * 3  terminate.
     * 4  terminate.
     * 5  terminate.
     * 6  terminate.
     * 7  terminate.
     * 8  terminate.
     * 9  terminate.
     * @param args
     */
    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domino(previous),String.valueOf(i));
            thread.start();
            previous = thread;
        }
    }

    static class Domino implements Runnable{

        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try{
                thread.join();
            }catch (InterruptedException e){

            }
            System.out.println(Thread.currentThread().getName() + "  terminate.");
        }
    }
}
