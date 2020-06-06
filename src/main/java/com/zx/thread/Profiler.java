package com.zx.thread;

import com.zx.util.SleepUtils;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 为当前线程根据一个ThreadLocal变量查询到绑定在这个线程上的一个值
 * 该方式可以复用在耗时统计功能上，方法入口执行前begin，执行后end
 * 好处是两个方法的调用不用在一个方法或类中，比如在AOp编程中
 */
public class Profiler {
    public static void main(String[] args) {
        Profiler.begin();
        SleepUtils.second(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        protected Long initialVaue(){
            return System.currentTimeMillis();
        }
    } ;

    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

}
