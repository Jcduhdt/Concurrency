package com.zx.thread;

import com.zx.util.SleepUtils;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 守护线程
 * 一种支持性线程，主要被用作程序中后台调度以及支持性工作
 * 当一个java虚拟机不存在非Daemon线程的时候，java虚拟机将会退出
 *
 * 在构建Daemon线程时，不能依靠finally块中的内容来确保执行关闭或清理资源得逻辑
 */
public class Daemon {
    // 运行该程序，在终端并没有出现任何输出，main在启动DaemonRunner线程后随着main执行完毕而终止
    // 此时所有Daemon都需要立即终止，DaemonRunner中的finally块并没有执行
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        // 设置该线程为守护线程
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable{
        @Override
        public void run() {
            try{
                SleepUtils.second(10);
            }finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
