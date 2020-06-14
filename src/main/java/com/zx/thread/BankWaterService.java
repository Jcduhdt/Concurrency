package com.zx.thread;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-14
 * 多线程计算数据，最后合并结果
 * 多线程处理每个sheet的银行流水
 * 执行完后得到每个sheet的日均流水，最后用barrierAction计算结果
 * 得到整张Excel的流水
 *
 * 为啥都运行完了，程序还在运行，没停止，是池吗
 * 把文章中的Executor改为ExecutorService，然后最后对他shutdown就行了
 */
public class BankWaterService implements Runnable {
    /**
     * 创建四个屏障，处理完后执行当前类的run方法
     */
    private CyclicBarrier c = new CyclicBarrier(4,this);

    /**
     * 假设只有4个sheet，所以只启动四个线程
     */
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * 保存每个sheet计算出的流水结果
     */
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count(){
        for (int i = 0; i < 4; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 计算当前sheet的流水，计算代码省略
                    sheetBankWaterCount.put(Thread.currentThread().getName(),1);
                    // 流水计算完成，插入屏障
                    try{
                        c.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    public void run() {
        int result = 0;
        // 汇总结果
        for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            result += sheet.getValue();
        }
        sheetBankWaterCount.put("result",result);
        System.out.println(result);
//        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        BankWaterService bankWaterCount = new BankWaterService();
        bankWaterCount.count();
    }
}
