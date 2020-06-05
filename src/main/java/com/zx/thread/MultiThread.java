package com.zx.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 使用JMX查看一个普通的Java程序包含那些线程
 *
 */
public class MultiThread {
    public static void main(String[] args) {
        // 获取java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }

        /*
        打印的信息
        [1]main main线程，用户程序入口
        [2]Reference Handler 清除Reference的线程
        [3]Finalizer 调用对象finalize方法的线程
        [4]Signal Dispatcher 分发处理发送给JVM信号的线程
        [5]Attach Listener
        [11]Common-Cleaner
        [12]Monitor Ctrl-Break
        */
    }
}
